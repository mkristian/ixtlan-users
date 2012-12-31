# -*- coding: utf-8 -*-
require 'bcrypt'
require 'manager'

class User < ActiveRecord::Base
  include BCrypt

  belongs_to :modified_by, :class_name => "User"

  has_and_belongs_to_many :groups

  # skip validation for the first user via rake db:seed
  validates :modified_by_id, :presence => true, :unless => Proc.new { |user| user.id == 1 }

  validates :login, :presence => true, :uniqueness => true, :format => /^[a-z0-9_\-.]+$/, :length => { :minmum => 4,:maximum => 32 }

  # TODO some format and is missing
  validates :email, :presence => true, :uniqueness => true, :length => { :maximum => 128 }
  validates :name, :presence => true, :length => { :maximum => 128 }, :format => /^[[:print:]]+$/

  validate :validate_everything_else

  def validate_everything_else
    if groups.member?(Group.AT) && at_token.blank?
      errors.add(:at_token, 'AT token can not be blank')
    end
    if !groups.member?(Group.AT) && !at_token.blank?
      errors.add(:groups, 'with given AT token the user must be member of the AT group')
    end  
  end

  def self.reset_password(login)
    u = User.find_by_login(login) || User.find_by_email(login)
    if u
      u.reset_password_and_save
      u # can be used for logging
    end
  end
  
  def self.assert( login, password )
    if password.blank?
      "no password given with login: #{login}"
    elsif login.blank?
      "no login given"
    end
  end
  
  def self.get_user( login, password )
    result = assert( login, password )
    if result
      result
    else
      u = User.find_by_login(login) || User.find_by_email(login)
      if u.nil?
        "login not found: #{login}"
      elsif u.hashed.nil?
        "user has no password: #{login}"
      else
        u
      end
    end
  end

  def self.check_password( user, password )
    if !user.hashed2.blank? && Password.new( user.hashed2 ) == password
      user.hashed = user.hashed2
    end
    if Password.new( user.hashed ) == password
      # clear reset password if any
      if user.hashed2
        user.hashed2 = nil
        user.save
      end
      user
    end 
  end

  def self.authenticate(login, password, token = nil)
    result = get_user( login, password )
    if result.is_a? User
      result = check_password( result, password )
      if result
        result.applications # setup app objects
        result.filter_groups(token)
      else
        result = "wrong password for login: #{login}"
      end
    end
    result
  end

  def self.filtered_all(current_user)
    users = includes(:groups)#.joins(:groups).where("groups_users.group_id" => current_user.groups)

    apps = current_user.allowed_applications

    # restict user list to AT unless current_user is user_admin
    # TODO maybe that should be part of the guard, i.e. 'all_users' action
    if !current_user.groups.member?(Group.USER_ADMIN)
      if current_user.groups.member?(Group.AT)
        users.delete_if { |user| !user.groups.member?(Group.AT) }
      elsif current_user.groups.member?(Group.APP_ADMIN)
        users.delete_if do |user| 
          g = user.groups.detect { |g| apps.member?(g.application) }
          g.nil?
        end
      end
    end

    if ! apps.empty? && ! current_user.root?
      users.each do |u|
        u.groups.delete_if do |g|
          ! apps.member?(g.application)
        end
      end
    end
    
    users
  end

  def self.filtered_find(id, current_user)
    filtered(find(id), current_user)
  end

  def self.filtered(user, current_user) 
    unless current_user.root?
      # restrict user to AT unless current_user is user_admin
      # TODO maybe that should be part of the guard, i.e. 'all_users' action
      if !current_user.groups.member?(Group.USER_ADMIN) && current_user.groups.member?(Group.AT)
        raise ActiveRecord::RecordNotFound("no AT user with id #{user.id}") unless user.groups.member?(Group.AT)
      end

      if current_user.groups.member?(Group.APP_ADMIN)
        apps = Group.APP_ADMIN.applications(current_user)
        if current_user.groups.member?(Group.USER_ADMIN)
          user.groups.delete_if do |g|
            ! (current_user.groups.member?(g) || apps.member?(g.application))
          end
        else
          user.groups.delete_if do |g|
            ! apps.member?(g.application)
          end
        end
      elsif current_user.groups.member?(Group.USER_ADMIN)
        user.groups.delete_if do |g|
          ! current_user.groups.member?(g)
        end
      end
    end
    user
  end

  def self.filtered_optimistic_find(updated_at, id, current_user)
    filtered(optimistic_find(updated_at, id), current_user)
  end

  def self.filtered_new(params, current_user)   
    manager = Manager.new(current_user)
    group_ids = manager.group_ids(nil, 
                                  :groups => params.delete(:groups),
                                  :group_ids => params.delete(:group_ids))
    params[:group_ids] = group_ids
    user = self.new(params)
    user.user_manager(manager) if manager
    user
  end
  
  def deep_save
    save && (user_manager ? user_manager.update(self) : true)
  end

  def deep_update_attributes(params, current_user)
    user_manager = Manager.new(current_user)
    groups = params.delete(:groups)
    group_ids = params.delete(:group_ids)
    group_ids = user_manager.group_ids(self, 
                                       :groups => groups, 
                                       :group_ids => group_ids)

    params[:group_ids] = group_ids
    update_attributes(params) && user_manager.update(self)
  end

  public

  def user_manager(manager = nil)
    @user_manager ||= manager if manager
    @user_manager
  end

  def filter_groups(token)
    if token
      app = Application.find_by_authentication_token(token)
      app_id = app ? app.id : 0
      app_ids =[app_id, Application.ALL.id]
      groups.delete_if do |g|
        !app_ids.member?(g.application.id)
      end
    end
  end

  def application_urls
    groups.collect do |g|
      g.application.url || "application #{g.application.name} has no configure url"
    end.uniq
  end

  def reset_password_and_save
    pwd = reset_password
    is_new = new_record?
    if deep_save
      if is_new
        UserMailer.send_new_user(self).deliver
        UserMailer.send_password(self).deliver
      else
        UserMailer.send_reset_password(self).deliver
      end
      @password = pwd
      pwd
    end
  end

  def self.all_changed_after(from, at_only = false)
    unless from.blank?
      if at_only
        User.joins(:groups).where('groups.id = ? AND users.updated_at > ?', Group.AT.id, from)
      else
        User.all(:conditions => ["updated_at > ?", from])
      end
    else
      if at_only
        User.joins(:groups).where('group.id' => Group.AT.id)
      else
        User.all
      end
    end
  end

  private

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

  public

  def password
    pwd = @password
    @password = nil
    pwd
  end

  def root?
    @is_root ||= groups.member? Group.ROOT
  end

  def app_admin?
    @is_app_admin ||= groups.member? Group.APP_ADMIN
  end

  def user_admin?
    @is_user_admin ||= groups.member? Group.USER_ADMIN
  end

  def at?
    @is_at ||= groups.member? Group.AT
  end

  def allowed_applications
    @allowed_apps ||= root? ? Application.all : Group.APP_ADMIN.applications(self)
  end

  def applications
    @applications ||= 
      begin
        apps = groups.collect { |g| g.application }.uniq
        apps << Application.ATS if at?
        apps
      end
  end

  def application_ids
    @application_ids ||= applications.collect { |a| a.id }
  end

  def group_ids
    @group_ids ||= groups.collect { |g| g.id }
  end

  # def self.update_options
  #   {
  #     :only => [:id, :login, :name, :updated_at]
  #   }
  # end

  # def self.at_update_options
  #   {
  #     :only => [:id, :name, :at_token, :updated_at], :root => 'at'
  #   }
  # end

  # def self.no_children_options
  #   {
  #     :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
  #     :methods => [ :applications ]
  #   }
  # end

  # def self.options
  #   {
  #     :except => [:hashed, :hashed2, :created_at, :modified_by_id],
  #     :methods => [:group_ids, :application_ids]
  #   }
  # end

  # def self.single_options
  #   {
  #     :except => [:hashed, :hashed2, :modified_by_id], 
  #     :include => { 
  #       :modified_by => {
  #         :only => [:id, :login, :name],
  #       },
  #       :groups => {
  #         :only => [:id, :name, :application_id],
  #         :include => {
  #           :application => {
  #             :only => [:id, :name]
  #           }
  #         },
  #         :methods => [:applications, :regions]
  #       }
  #     }
  #   }
  # end

  # def self.remote_options
  #   {
  #     :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id, :at_token, :email],
  #     :include => {
  #       :groups => {
  #         :only => [:id, :name],
  #         :methods => [:regions]
  #       }
  #     },
  #     :methods => [ :applications ],
  #   }
  # end

  # unless respond_to? :old_as_json
  #   alias :old_as_json :as_json
  #   def as_json(options = nil)
  #     options = self.class.no_children_options unless options
  #     setup_groups(options)
  #     old_as_json(options)
  #   end
  # end

  # unless respond_to? :old_to_json
  #   alias :old_to_json :to_json
  #   def to_json(options = nil)
  #     setup_groups(options)
  #     old_to_json(options)
  #   end

  #   def setup_groups(options = {})
  #     methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
     
  #     groups.each { |g| g.applications(self) } if methods.member? :applications
  #     groups.each { |g| g.application_ids(self) } if methods.member? :application_ids
  #     groups.each { |g| g.regions(self) } if methods.member? :regions
  #   end

  #   alias :old_to_xml :to_xml
  #   def to_xml(options = nil) 
  #     setup_groups(options)
  #     old_to_xml(options)
  #   end
  # end

  private
  def generate_password
    length = 12
    password = Array.new(length).map { (65 + rand(58)).chr }.join
    password = generate_password unless password =~ /^[a-zA-Z]+$/
    password
  end
end
