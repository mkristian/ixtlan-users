class RemotePermissionsController < ApplicationController

  before_filter :cleanup_params

  before_filter :authorize_application, :except => [:index]
  skip_before_filter :authorize, :except => [:index]

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:remote_permission] || []
    model.delete :id
    model.delete :created_at
    params[:updated_at] = model.delete :updated_at
  end

  def stale?
    if @remote_permission.nil?
      @remote_permission = RemotePermission.filtered_find(params[:id], @application)
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      true
    end
  end

  public

  # GET /remote_permissions
  # GET /remote_permissions.xml
  # GET /remote_permissions.json
  def index
    @remote_permissions = RemotePermission.filtered_all(current_user)

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @remote_permissions.to_xml(RemotePermission.options) }
      format.json  { render :json => @remote_permissions.to_json(RemotePermission.options) }
    end
  end

  # GET /remote_permissions/1
  # GET /remote_permissions/1.xml
  # GET /remote_permissions/1.json
  def show
    @remote_permission = RemotePermission.filtered_find(params[:id], @application)

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @remote_permission.to_xml(RemotePermission.single_options) }
      format.json  { render :json => @remote_permission.to_json(RemotePermission.single_options) }
    end
  end

  # GET /remote_permissions/new
  def new
    @remote_permission = RemotePermission.new
  end

  # GET /remote_permissions/1/edit
  def edit
    @remote_permission = RemotePermission.filtered_find(params[:id], @application)
  end

  # POST /remote_permissions
  # POST /remote_permissions.xml
  # POST /remote_permissions.json
  def create
    @remote_permission = RemotePermission.new(params[:remote_permission])
    @remote_permission.modified_by = current_user
    @remote_permission.application = @application

    respond_to do |format|
      if @remote_permission.save
        format.html { redirect_to(@remote_permission, :notice => 'Remote permission was successfully created.') }
        format.xml  { render :xml => @remote_permission.to_xml(RemotePermission.single_options), :status => :created, :location => @remote_permission }
        format.json  { render :json => @remote_permission.to_json(RemotePermission.single_options), :status => :created, :location => @remote_permission }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @remote_permission.errors, :status => :unprocessable_entity }
        format.json  { render :json => @remote_permission.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /remote_permissions/1
  # PUT /remote_permissions/1.xml
  # PUT /remote_permissions/1.json
  def update
    @remote_permission = RemotePermission.filtered_optimistic_find(params[:updated_at], params[:id], @application)

    return if stale?

    params[:remote_permission] ||= {}
    params[:remote_permission][:modified_by] = current_user

    authorize_application_param(params[:remote_permission])

    respond_to do |format|
      if @remote_permission.update_attributes(params[:remote_permission])
        format.html { redirect_to(@remote_permission, :notice => 'Remote permission was successfully updated.') }
        format.xml  { render :xml => @remote_permission.to_xml(RemotePermission.single_options) }
        format.json  { render :json => @remote_permission.to_json(RemotePermission.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @remote_permission.errors, :status => :unprocessable_entity }
        format.json  { render :json => @remote_permission.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /remote_permissions/1
  # DELETE /remote_permissions/1.xml
  # DELETE /remote_permissions/1.json
  def destroy
    @remote_permission = RemotePermission.optimistic_find(params[:updated_at], params[:id], @application)

    return if stale?

    @remote_permission.destroy

    respond_to do |format|
      format.html { redirect_to(remote_permissions_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
