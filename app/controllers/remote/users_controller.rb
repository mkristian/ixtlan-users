class Remote::UsersController < Remote::ApplicationController

  public

  # GET /users/last_changes
  def last_changes
    @users = serializer( User.all_changed_after( params[ :updated_at ] ) ).use( :update )
    respond_with @users
  end
end
