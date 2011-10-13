class AuthenticationsController < ApplicationController

  skip_before_filter :authorization
  before_filter :ip_restriction

  protected

  # TODO do not know why skip_before_filter does not work with heroku
  def authorization
    true
  end

  public

  def create
    user = User.authenticate(params[:authentication][:login],
                             params[:authentication][:password],
                             request.remote_ip)
      
    # for the log
    @authentication = user

    if user.valid?

      respond_to do |format|
        format.xml  { render :xml => user.to_xml(User.remote_options) }
        format.json  { render :json => user.to_json(User.remote_options) }
      end
    else
      head :unauthorized
    end
  end

  def reset_password
    auth = params[:authentication] || params
    @authentication = auth[:email] || auth[:login]
    pwd = User.reset_password(@authentication)

    if pwd
      render :inline => pwd
    else
      head :not_found
    end
  end
end
