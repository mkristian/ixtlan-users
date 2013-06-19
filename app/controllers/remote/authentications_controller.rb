class Remote::AuthenticationsController < Remote::ApplicationController

  def create
    args = login_and_password + [ x_service_token ]
    @authentication = User.authenticate( *args )

    if @authentication.is_a? User
      respond_with serializer( @authentication ).use( :authenticate )

    else
      head :unauthorized
    end
  end

  def reset_password
    username = login_and_password[ 0 ]

    @authentication = "login/email: #{username}"

    pwd = User.reset_password( username )

    if pwd
      render :inline => "password sent", :content_type => :text
    else
      head :not_found
    end
  end
end
