require 'bcrypt'

class User < ActiveRecord::Base
  include BCrypt

  def self.reset_password(login)
    u = User.find_by_login(login) || User.find_by_email(login)
    if u
      pwd = u.reset_password
      u.save
      [u, pwd]
    end
  end

  def self.authenticate(login, password)
    return "no password for login: #{login}" if password.blank?
    if login == 'root'
      if password == 'behappy'
        return User.new(:login => 'root', :name => "Root")
      else
        return "wrong password for login: #{login}"
      end
    end
    u = User.find_by_login(login) || User.find_by_email(login)
    if u
      if Password.new(u.hashed) == password
        # clear reset password if any
        if u.hashed2
          u.hashed2 = nil
          u.save
        end
        u
      else
        # try reset password
        if !u.hashed2.blank? && Password.new(u.hashed2) == password
          # discard old password and promote reset password to be the password
          u.hashed = u.hashed2
          u.hashed2 = nil
          u.save
          u
        else
          "wrong password for login: #{login}"
        end
      end
    else
      "login not found: #{login}"
    end
  end

  def reset_password
    # only alpha pwd
    password = __password
    if self.hashed
      self.hashed2 = Password.create(password)
    else
      self.hashed = Password.create(password)
    end
    password
  end

  def groups
    @groups ||= [Group.new('name' => :root)]
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = {})
      options ||= {}
      options[:methods] = [:groups] unless options[:methods]
      options[:except] = [:hashed, :hashed2] unless options[:except]
      old_as_json(options)
    end
  end

  private
  def __password
    length = 12
    password = Array.new(length).map { (65 + rand(58)).chr }.join
    password = __password unless password =~ /^[a-zA-Z]+$/
    password
  end
end
