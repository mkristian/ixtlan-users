class SessionsController < ApplicationController

  skip_before_filter :authorization

  skip_before_filter :check_session, :only => :destroy

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
                              auth[:password]) do |user|
      current_user(user)
      group_names = groups_for_current_user
#user.groups.collect { |g| g.name.to_s }
      guard.permissions(group_names)
    end
    @session.idle_session_timeout = Rails.application.config.idle_session_timeout

    if @session.valid?
      # TODO make html login
      respond_to do |format|
        format.html { render :inlinetext => "authorized" }
        format.xml  { render :xml => @session.to_xml }
        format.json  { render :json => @session.to_json }
      end
    else
      head :unauthorized
    end
  end

  def current_groups
    groups = current_user.groups

    if groups.detect {|g| g.name == 'root'}
      groups = Group.all
    end

    # for the log
    @session = groups

    respond_to do |format|
      format.html { render :inlinetext => "not implemented" }
      format.xml  { render :xml => groups.to_xml(Group.options) }
      format.json  { render :json => groups.to_json(Group.options) }
    end
  end

  def destroy
    # for the log
    @session = user

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
