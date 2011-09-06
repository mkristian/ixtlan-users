class AuthenticationsController < ApplicationController

  skip_before_filter :authorization

  # TODO do not know why skip_before_filter does not work with heroku
  def authorization
    true
  end

  def create
    @user = User.authenticate(params[:authentication][:login],
                              params[:authentication][:password] )

    if @user
      respond_to do |format|
        format.xml  { render :xml => @user.to_xml }
        format.json  { render :json => @user.to_json }
      end
    else
      head :not_found
    end
  end
end
