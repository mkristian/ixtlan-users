class ProfileController < LocalController

#TODO  cache_headers :private

  # GET /profile
  def show
    @profile = current_user

    respond_with serializer( @profile ).use( :profile )
  end

  # PUT /profile
  def update
    @profile = current_user

    profile_params = params[ :profile ]
    profile_params.delete( :login )

    # TODO too much business logic here
    user = User.authenticate( @profile.login,
                              profile_params.delete( :password ) )

    if user == @profile
      if @profile.update_attributes( profile_params )

        respond_with serializer( @profile ).use( :profile )
      end
    else
      head :unauthorized
    end
  end
end
