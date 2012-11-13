class Remote::AuthenticationsController < Remote::ApplicationController

  public

  def create
    # for the log use authentication as name
    @authentication = User.authenticate((params[:authentication] || {})[:login] || params[:login],
                                        (params[:authentication] || {})[:password] || params[:password],
                                        x_service_token)

    if @authentication.log.nil?

      respond_with serializer( @authentication ).use( :authenticate )

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
