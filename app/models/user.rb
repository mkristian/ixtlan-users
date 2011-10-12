require 'bcrypt'

class User < ActiveRecord::Base
  include BCrypt

  belongs_to :modified_by, :class_name => "User"

  # skip validation for the first user via rake db:seed
  validates :modified_by_id, :presence => true, :unless => Proc.new { |user| user.id == 1 }

  has_and_belongs_to_many :groups

  def self.reset_password(login)
    u = User.find_by_login(login) || User.find_by_email(login)
    if u
      pwd = u.reset_password
      if u.save
        UserMailer.send_password(u).deliver
        pwd
      end
    end
  end

  def self.authenticate(login, password)
    result = User.new
    if password.blank?
      result.log = "no password given with login: #{login}"
    else
      u = User.find_by_login(login) || User.find_by_email(login)
      if u.hashed.nil?
        result.log = "user has not password: #{login}"
      elsif u
        if Password.new(u.hashed) == password
          # clear reset password if any
          if u.hashed2
            u.hashed2 = nil
            u.save
          end
          result = u
        else
          # try reset password
          if !u.hashed2.blank? && Password.new(u.hashed2) == password
            # discard old password and promote reset password to be the password
            u.hashed = u.hashed2
            u.hashed2 = nil
            u.save
            result = u
          else
            result.log = "wrong password for login: #{login}"
          end
        end
      else
        result.log = "login not found: #{login}"
      end
    end
    result
  end
  
  def log=(msg)
    @log = msg
  end

  def to_log
    if @log
      @log
    else
      self.inspect
    end
  end

  def reset_password
    # only alpha pwd
    @password = generate_password
    if self.hashed
      self.hashed2 = Password.create(@password)
    else
      self.hashed = Password.create(@password)
    end
    @password
  end

  def password
    pwd = @password
    @password = nil
    pwd
  end

  def self.update_options
    {
      :only => [:id, :login, :name, :updated_at]
    }
  end

  def self.options
    {
      :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id]
    }
  end

  def self.single_options
    {
      :except => [:hashed, :hashed2, :modified_by_id], 
      :include => { 
        :modified_by => {
          :only => [:id, :login, :name],
        },
        :groups => {
          :only => [:id, :name]
        }
      }
    }
  end

  def self.nested_options
    {
      :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id], 
      :include => {
        :groups => {
          :only => [:id, :name]
        }
      }
    }
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = nil)
      options = self.class.nested_options unless options
      old_as_json(options)
    end
  end

  private
  def generate_password
    length = 12
    password = Array.new(length).map { (65 + rand(58)).chr }.join
    password = generate_password unless password =~ /^[a-zA-Z]+$/
    password
  end
end
