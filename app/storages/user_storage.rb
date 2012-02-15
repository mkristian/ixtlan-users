require 'delegate'
class UserStorage

  class UserDelegator < SimpleDelegator
    def initialize(model, manager)
      @manager = manager
      super(model)
    end
  
    def save
      super && @manager.update(self)
    end
    
    def update_attributes(params)
      groups = params.delete(:groups)
      group_ids = params.delete(:group_ids)
      group_ids = @manager.group_ids(self, 
                                     :groups => groups, 
                                     :group_ids => group_ids)
      
      params[:group_ids] = group_ids
      super(params) && @manager.update(self)
    end
  end

  def initialize(current_user)
    @current_user = current_user
  end
  
  def all
    users = User.includes(:groups)#.joins(:groups).where("groups_users.group_id" => current_user.groups)

    apps = @current_user.allowed_applications

    # restict user list to AT unless current_user is user_admin
    # TODO maybe that should be part of the guard, i.e. 'all_users' action
    if !@current_user.user_admin?
      if @current_user.at?
        users.delete_if { |user| !user.at? }
      elsif @current_user.groups.app_admin?
        users.delete_if do |user| 
          g = user.groups.detect { |g| apps.member?(g.application) }
          g.nil?
        end
      end
    end

    if ! apps.empty? && ! @current_user.root?
      users.each do |u|
        u.groups.delete_if do |g|
          ! apps.member?(g.application)
        end
      end
    end
    
    users
  end

  def find(id)
    filtered(User.find(id))
  end


  def optimistic_find(updated_at, id)
    filtered(User.optimistic_find(updated_at, id))
  end

  def new_user(params)   
    manager = Manager.new(@current_user)
    group_ids = manager.group_ids(nil, 
                                  :groups => params.delete(:groups),
                                  :group_ids => params.delete(:group_ids))
    params[:group_ids] = group_ids
    UserDelegator.new(User.new(params), manager)
  end

  private

  def filtered(user) 
    unless @current_user.root?
      # if current_user is AT restrict user to be AT
      # unless current_user is user_admin
      # TODO maybe that should be part of the guard, i.e. 'all_users' action
      if !@current_user.groups.member?(Group.USER_ADMIN) && @current_user.groups.member?(Group.AT)
        raise ActiveRecord::RecordNotFound("no AT user with id #{user.id}") unless user.groups.member?(Group.AT)
      end

      if @current_user.groups.member?(Group.APP_ADMIN)
        apps = Group.APP_ADMIN.applications(current_user)
        if @current_user.groups.member?(Group.USER_ADMIN)
          user.groups.delete_if do |g|
            ! (@current_user.groups.member?(g) || apps.member?(g.application))
          end
        else
          user.groups.delete_if do |g|
            ! apps.member?(g.application)
          end
        end
      elsif @current_user.groups.member?(Group.USER_ADMIN)
        user.groups.delete_if do |g|
          ! @current_user.groups.member?(g)
        end
      end
    end
    UserDelegator.new(user, Manager.new(@current_user))
  end
end
