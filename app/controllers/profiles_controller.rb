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

  # PUT /profiles
  # PUT /profiles.xml
  # PUT /profiles.json
  def update
    @profile = current_user

    profile = params[:profile] ||[]
    profile.delete(:created_at)
    profile.delete(:updated_at)
    profile.delete(:login)
    profile.delete(:id)

    user = User.authenticate(@profile.login, profile.delete(:password))
    if user == @profile
      respond_to do |format|
        if @profile.update_attributes(profile)
          format.html { redirect_to(profile_path, :notice => 'Profile was successfully updated.') }
          format.xml  { render :xml => @profile }
          format.json  { render :json => @profile.to_json(:root => :profile, :methods => []) }
        else
          format.html { render :action => "edit" }
          format.xml  { render :xml => @profile.errors, :status => :unprocessable_entity }
          format.json  { render :json => @profile.errors, :status => :unprocessable_entity }
        end
      end
    else
      head :unauthorized
    end
  end
end
