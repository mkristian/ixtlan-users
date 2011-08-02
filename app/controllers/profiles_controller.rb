class ProfilesController < ApplicationController
 
  # GET /profiles
  # GET /profiles.xml
  # GET /profiles.json
  def show
    @profile = current_user

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @profile }
      format.json  { render :json => @profile.to_json(:root => :profile, :methods => []) }
    end
  end

  # GET /profiles/edit
  def edit
    @profile = current_user
  end

  # PUT /profiles/reset_password
  # PUT /profiles/reset_password.xml
  # PUT /profiles/reset_password.json
  def reset_password
    @profile = current_user
    pwd = @profile.reset_password
    UserMailer.send_password_email(@profile, pwd)

    head :ok
  end

  # PUT /profiles
  # PUT /profiles.xml
  # PUT /profiles.json
  def update
    @profile = current_user

    profile = params[:profile] ||[]
    profile.delete(:updated_at)
#    profile.delete(:openid_identifier)
#    new_password = 
profile.delete(:new_password)
    user = User.authenticate(@profile.login, profile.delete(:password))
  
    if user == @profile
 #     profile[:password] = new_password if new_password
      respond_to do |format|
        if @profile.update_attributes(params[:profile])
          format.html { redirect_to(profile_path, :notice => 'Profile was successfully updated.') }
          format.xml  { render :xml => @profile }
          format.json  { render :json => @profile.to_json(:root => :profile, :methods => []) }
        else
          format.html { render :action => "edit" }
          format.xml  { render :xml => @profile.errors, :status => :unprocessable_entity }
          format.json  { render :json => @profile.errors, :status => :unprocessable_entity }
        end
      end
    end
  end
end
