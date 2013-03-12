class UsersController < LocalController

  private

  def cleanup
    # compensate the shortcoming of the incoming json/xml
    model = params[:user] || []
    model.delete :application_ids #not needed
    model.delete :applications #not needed
    model.delete :created_at # TODO should be part of super#cleanup
  end

  public

  # GET /users
  # GET /users.xml
  # GET /users.json
  def index
    @users = User.filtered_all( current_user )

    respond_with serializer( @users )
  end

  # GET /users/1
  # GET /users/1.xml
  # GET /users/1.json
  def show
    @user = User.filtered_find(params[:id], current_user)

    respond_with serializer( @user )
  end

  # GET /users/1/at
  # GET /users/1/at.xml
  # GET /users/1/at.json
  def at
    @user = User.filtered_find(params[:id], current_user)
    
    respond_with serializer( @user )
  end

  # POST /users
  # POST /users.xml
  # POST /users.json
  def create
    @user = User.filtered_new(params[:user], current_user)
    @user.modified_by = current_user

    @user.save

    respond_with serializer( @user )
  end

  # PUT /users/1
  # PUT /users/1.xml
  # PUT /users/1.json
  def update
    params[:user] ||= params
    @user = User.filtered_optimistic_find(params[:user][:updated_at], 
                                          params[:id], 
                                          current_user)

    params[:user][:modified_by] = current_user

    #TODO allowed? should be part of guard
    unless guard.allowed?("users", "change", current_user.groups)
      params[:user].delete(:login)
      params[:user].delete(:email)
    end

    @user.updated_at = Time.now

p params[:user]
    @user.deep_update_attributes(params[:user], current_user)

    respond_with serializer( @user )
  end

  # PUT /users/1/reset_password.xml
  # PUT /users/1/reset_password.json
  def reset_password
    params[:user] ||= params
    @user = User.optimistic_find(params[:user][:updated_at], params[:id])

    @user.reset_password_and_save

    respond_with serializer( @user )
  end

  # DELETE /users/1
  # DELETE /users/1.xml
  # DELETE /users/1.json
  def destroy
    params[:user] ||= params
    @user = User.optimistic_find(params[:user][:updated_at], params[:id])

    @user.destroy

    head :ok
  end
end
