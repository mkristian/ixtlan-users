class SessionsController < ApplicationController

  skip_before_filter :authorization

  prepend_after_filter :reset_session, :only => :destroy

  protected

  # TODO do not know why skip_before_filter does not work with heroku
  def authorization
    true
  end

  public

  def create
    auth = params[:authentication] || []
    @session = Session.create(auth[:login] || auth[:email], 
                              auth[:password])
    case @session
    when Session
      current_user(@session.user)
      groups = @session.user.groups.collect { |g| g.name.to_s }
      @session.permissions = guard.permissions(groups)

      # TODO make html login
      respond_to do |format|
        format.html { render :text => "authorized" }
        format.xml  { render :xml => @session.to_xml }
        format.json  { render :json => @session.to_json }
      end
    else
      def @session.to_log
        self
      end
      head :unauthorized
    end
  end

  def destroy
    # reset session happens in the after filter which allows for 
    # audit log with username which happens in another after filter
    head :ok
  end

  def reset_password
    authentication = params[:authentication] || []
    user, pwd = User.reset_password(authentication[:email] || authentication[:login])

    if user
      UserMailer.send_password(user, pwd).deliver
      
      # for the log
      @session = user
      
      head :ok
    else
      head :not_found
    end
  end
end
