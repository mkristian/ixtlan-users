class SessionsController < LocalController

  skip_before_filter :authorize

  skip_before_filter :check_session, :only => :destroy

  skip_after_filter :audit, :only => :ping

  prepend_after_filter :reset_session, :only => :destroy

  protected

  # TODO do not know why skip_before_filter does not work with heroku
  def authorize
    true
  end

  public

  def create
    user = User.authenticate( *login_and_password )
    if user.is_a? User
      current_user( user )
      @session = Session.new( 'user' => user,
                              'idle_session_timeout' => Users::Application.config.idle_session_timeout,
                              'permissions' => guard.permissions( user.groups) )
      
      respond_with serializer( @session )
    else
      @session = user.to_s
      head :unauthorized
    end
  end

  def ping
    head :ok
  end

  def destroy
    # for the log
    @session = current_user

    # reset session happens in the after filter which allows for 
    # audit log with username which happens in another after filter
    head :ok
  end

  def reset_password
    if @session = User.reset_password( login_and_password[ 0 ] )
      head :ok
    else
      head :not_found
    end
  end
end
