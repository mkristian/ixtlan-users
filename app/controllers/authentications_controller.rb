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
                             params[:authentication][:password] )

    if user.is_a? User
      
      # for the log
      @authentication = user

      respond_to do |format|
        format.xml  { render :xml => user.to_xml }
        format.json  { render :json => user.to_json }
      end
    else
      head :not_found
    end
  end

  def reset_password
    _params = params[:authentication] || params
    @authentication = _params[:email] || _params[:login]
    pwd = User.reset_password(@authentication)

    if pwd
      render :inline => pwd
    else
      head :not_found
    end
  end
end
