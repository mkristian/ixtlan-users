class SessionsController < ApplicationController

  skip_before_filter :authorization

  skip_before_filter :check_session, :only => :destroy

  skip_after_filter :audit, :only => :ping

  prepend_after_filter :reset_session, :only => :destroy

  protected

  # TODO do not know why skip_before_filter does not work with heroku
  def authorization
    true
  end

  public

  def create
    auth = params[:authentication] || params
    @session = Session.create(auth[:login] || auth[:email], 
                              auth[:password])

    if @session.valid?
      current_user(@session.user)
      @session.idle_session_timeout = Rails.application.config.idle_session_timeout
      @session.permissions = guard.permissions(current_user_group_names)

      # TODO make html login
      respond_to do |format|
        format.html { render :inline => "authorized" }
        format.xml  { render :xml => @session.to_xml }
        format.json  { render :json => @session.to_json }
      end
    else
      head :unauthorized
    end
  end

  def ping
    head :ok
  end

  def destroy
    # for the log
    @session = current_user
    
    def @session.to_log
      "Session(user-id: #{id})"
    end

    # reset session happens in the after filter which allows for 
    # audit log with username which happens in another after filter
    head :ok
  end

  def reset_password
    authentication = params[:authentication] || []
    user = User.reset_password(authentication[:email] || authentication[:login])

    if user

      # for the log
      @session = user
      
      head :ok
    else
      head :not_found
    end
  end
end
