class AuthenticationsController < ApplicationController

  skip_before_filter :authorize
  before_filter :remote_permission

  protected

  # TODO do not know why skip_before_filter does not work with heroku
  def authorize
    true
  end

  public

  def create
    # for the log use authentication as name
    @authentication = User.authenticate(params[:authentication][:login],
                                        params[:authentication][:password],
                                        x_service_token)

    if @authentication.valid?

      respond_to do |format|
        format.xml  { render :xml => @authentication.to_xml(User.remote_options) }
        format.json  { render :json => @authentication.to_json(User.remote_options) }
      end
    else
      head :unauthorized
    end
  end

  def reset_password
    auth = params[:authentication] || params
    @authentication = auth[:email] || auth[:login]
    def @authentication.to_log
      "login/email: #{to_s}"
    end
    pwd = User.reset_password(@authentication)
    
    if pwd
      render :inline => "password sent", :content_type => :text
    else
      head :not_found
    end
  end
end
