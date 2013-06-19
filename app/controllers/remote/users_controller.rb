class Remote::UsersController < Remote::ApplicationController

  # GET /users/last_changes
  def last_changes
    @users = User.all_changed_after( params[ :updated_at ] )
    respond_with serializer( @users ).use( :update )
  end

  # GET /users/last_changes_of_app
  def last_changes_of_app
    @users = User.all_changed_after_of_app( params[ :updated_at ], remote_permission )
    respond_with serializer( @users ).use( :for_app )
  end

  def setup
    @user = User.filtered_setup( params,
                                 remote_permission,
                                 User.system_user )
    respond_with serializer( @user ).use( :for_app )
  end
end
