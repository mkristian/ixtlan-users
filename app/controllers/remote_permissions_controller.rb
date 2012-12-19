class RemotePermissionsController < LocalController

  before_filter :authorize_application, :except => [:index]
  skip_before_filter :authorize, :except => [:index]

  # GET /remote_permissions
  def index
    @remote_permissions = RemotePermission.filtered_all(current_user)

    respond_with serializer( @remote_permissions )
  end

  # GET /remote_permissions/1
  def show
    @remote_permission = RemotePermission.filtered_find( params[:id], 
                                                         @application )

    respond_with serializer( @remote_permission )
  end

  # POST /remote_permissions
  def create
    @remote_permission = RemotePermission.new( params[:remote_permission] )
    @remote_permission.modified_by = current_user
    @remote_permission.application = @application

    @remote_permission.save

    respond_with serializer( @remote_permission )
  end

  # PUT /remote_permissions/1
  def update
    @remote_permission = 
      RemotePermission.filtered_optimistic_find( updated_at, 
                                                 params[:id], 
                                                 @application )
    
    params[:remote_permission][:modified_by] = current_user

    authorize_application_param( params[:remote_permission] )

    @remote_permission.update_attributes( params[:remote_permission] )

    respond_with serializer( @remote_permission )
  end

  # DELETE /remote_permissions/1
  def destroy
    @remote_permission = 
      RemotePermission.filtered_optimistic_find( updated_at, 
                                                 params[:id], 
                                                 @application )

    @remote_permission.destroy

    head :ok
  end
end
