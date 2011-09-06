class SessionsController < ApplicationController

  skip_before_filter :authorization

  prepend_after_filter :reset_session, :only => :destroy

  protected
  
  def open_id_authentication(openid_url)
    authenticate_with_open_id(openid_url, 
                              :required => ['http://axschema.org/namePerson/first', 
                                            'http://axschema.org/namePerson/last', 
                                            'http://axschema.org/contact/email']) do |result, identity_url, registration, ax|
      if result.successful?
        #TODO set identity_url from openid
        user = User.first#User.find_by_identity_url(identity_url)
        if user
          dirty = false
          if email = ax["http://axschema.org/contact/email"]
            dirty = (user.email != email.to_s)
            user.email = email.to_s
          end
          if !(first = ax["http://axschema.org/namePerson/first"]).nil? &&
              !(last = ax["http://axschema.org/namePerson/last"]).nil?
            name = "#{first} #{last}".strip
            dirty = dirty || (user.name != name)
            user.name = name
          end
          user.save if dirty
          session = Session.new
          session.user = user
          session
        else
          "user not found"
        end
      else
        result.message
      end
    end
  end

  public

  def show
    params[:openid_url]='https://www.google.com/accounts/o8/id?id=AItOawl0ZAki6fUK3JlAwaF0zRas6qB_AL6_XqY' #'https://www.google.com/accounts/o8/id'
    create
  end

  def create
    @session = if using_open_id?
                 open_id_authentication(params[:openid_url])
               else
                 auth = params[:authentication] || []
                 Session.create(auth[:login] || auth[:email], 
                                auth[:password])
               end
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
