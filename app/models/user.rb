# -*- coding: utf-8 -*-
require 'bcrypt'
require 'manager'

class User < ActiveRecord::Base
  include BCrypt

  belongs_to :modified_by, :class_name => "User"

  # skip validation for the first user via rake db:seed
  validates :modified_by_id, :presence => true, :unless => Proc.new { |user| user.id == 1 }

  has_and_belongs_to_many :groups

  def self.reset_password(login)
    u = User.find_by_login(login) || User.find_by_email(login)
    if u
      u.reset_password_and_save
      u # can be used for logging
    end
  end
  
  def self.authenticate(login, password, token = nil)
    result = User.new
    if password.blank?
      result.log = "no password given with login: #{login}"
    elsif login.blank?
      result.log = "no login given"
    else
      u = User.find_by_login(login) || User.find_by_email(login)
      if u && u.hashed.nil?
        result.log = "user has no password: #{login}"
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
    if result.valid?
      result.applications #memorize apps
      result.filter_groups(token)
    end
    result
  end

  def self.filtered_all(current_user)
    users = includes(:groups)#.joins(:groups).where("groups_users.group_id" => current_user.groups)

    apps = current_user.root_group_applications
    if ! apps.empty? && ! apps.member?(Application.ALL)
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
    apps = current_user.root_group_applications
    if ! apps.empty? && ! apps.member?(Application.ALL)
      user.groups.delete_if do |g|
        ! apps.member?(g.application)
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

  def user_manager(manager = nil)
    @user_manager ||= manager if manager
    @user_manager
  end

  def filter_groups(token)
    if token
      perm = RemotePermission.find_by_token(token)
      app_id = (perm && perm.application) ? perm.application.id : 0
      app_ids =[app_id, Application.ALL.id]
      groups.delete_if do |g|
        !app_ids.member?(g.application.id)
      end
    end
  end

  def log=(msg)
    @log = msg
  end

  def to_log
    if @log
      @log
    else
      "User(#{self.id})"
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
      else
        UserMailer.send_password(self).deliver
      end
      @password = pwd
      pwd
    end
  end

  def self.all_changed_after(from)
    unless from.blank?
      User.all(:conditions => ["updated_at > ?", from])
    else
      User.all
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

  def user_admin?
    @is_user_admin ||= groups.member? Group.USER_ADMIN
  end

  def root_group_applications
    @root_group_apps ||= groups.member?(Group.ROOT) ? Group.ROOT.applications(self) : []
  end

  def applications
    @applications ||= 
      begin
        if root_group_applications.member? Application.ALL
          Application.all.select { |a| not a.url.blank? }
        else
          groups.collect { |g| g.application }.uniq
        end
      end
  end

  def application_ids
    @application_ids ||= applications.collect { |a| a.id }
  end

  def group_ids
    @group_ids ||= groups.collect { |g| g.id }
  end

  def self.update_options
    {
      :only => [:id, :login, :name, :updated_at]
    }
  end

  def self.no_children_options
    {
      :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
      :methods => [ :applications ]
    }
  end

  def self.options
    {
      :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
      :methods => [:group_ids, :application_ids]
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
          :only => [:id, :name, :application_id],
          :include => {
            :application => {
              :only => [:id, :name]
            }
          },
          :methods => [:applications]
        }
      }
    }
  end

  def a
   Application.first
  end

  def self.remote_options
    {
      :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
      :include => {
        :groups => {
          :only => [:id, :name],
          :methods => [:regions]
        }
      },
      :methods => [ :applications ],
    }
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = nil)
      options = self.class.no_children_options unless options
      setup_groups(options)
      old_as_json(options)
    end
  end

  unless respond_to? :old_to_json
    alias :old_to_json :to_json
    def to_json(options = nil)
      setup_groups(options)
      old_to_json(options)
    end

    def setup_groups(options = {})
      methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
      groups.each { |g| g.applications(self) } if methods.member? :applications
      groups.each { |g| g.application_ids(self) } if methods.member? :application_ids
      groups.each { |g| g.regions(self) } if methods.member? :regions
    end

    alias :old_to_xml :to_xml
    def to_xml(options = nil) 
      setup_groups(options)
      old_to_xml(options)
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
