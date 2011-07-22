class AuthenticationsController < ApplicationController

  def create
    @user = User.authenticate(params[:authentication][:login],
                              params[:authentication][:password] )

    if @user
      respond_to do |format|
        format.xml  { render :xml => @user.to_xml(:include => :groups) }
        format.json  { render :json => @user.to_json(:include => :groups) }
      end
    else
      head :not_found
    end
  end
end
