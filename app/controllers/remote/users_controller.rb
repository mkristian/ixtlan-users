class Remote::UsersController < Remote::ApplicationController

  # GET /users/last_changes
  def last_changes
    @users = User.all_changed_after( params[ :updated_at ] )
    respond_with serializer( @users ).use( :update )
  end
end
