class SessionsController < ApplicationController

  skip_before_filter :authorization

  prepend_after_filter :reset_session, :only => :destroy

  protected
  
  def open_id_authentication(openid_url)
    authenticate_with_open_id(openid_url, 
                              :required => [:nickname, 'http://axschema.org/contact/email']) do |result, identity_url, registration|
      if result.successful?
p registration
p registration.methods.sort

p registration['email']
p identity_url
        #TODO set email from openid
        user = User.first#User.find_by_identity_url(identity_url)
        if user
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
                 Session.create(params[:authentication])
               end
puts "asddddd"
p @session
    case @session
    when Session
      current_user(@session.user)
      @session.permissions = guard.permissions(self)

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
    @session = Session.find(params[:id])

    pwd = @session.user.reset_password
    UserMailer.send_password_email(@session.user, pwd)

    head :ok
  end
end
