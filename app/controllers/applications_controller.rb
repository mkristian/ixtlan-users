class ApplicationsController < ApplicationController

  before_filter :cleanup_params

  before_filter :authorize_application, :except => [:index, :new, :create]

  skip_before_filter :authorize, :except => [:index, :new, :create]

  private

  def authorize_application
    #TODO super(params[:id])
    if params[:id]
      @application = 
        if params[:updated_at]
          Application.optimistic_find(params[:updated_at], params[:id])
        else
          Application.find(params[:id])
        end
      authorize_app(@application)
      @application
    end
  end

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:application] || []
    model.delete :id
    model.delete :created_at
    params[:updated_at] ||= model.delete :updated_at
  end

  def stale?
    if @application.nil?
      @application = Application.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      true
    end
  end

  public

  # GET /applications/last_changes.xml
  # GET /applications/last_changes.json
  def last_changes
    @users = Application.all_changed_after(params[:updated_at])

    respond_to do |format|
      format.xml  { render :xml => @users.to_xml(Application.update_options) }
      format.json  { render :json => @users.to_json(Application.update_options) }
    end
  end

  # GET /applications
  # GET /applications.xml
  # GET /applications.json
  def index
    @applications = Application.filtered_all(current_user)

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @applications.to_xml(Application.options) }
      format.json  { render :json => @applications.to_json(Application.options) }
    end
  end

  # GET /applications/1
  # GET /applications/1.xml
  # GET /applications/1.json
  def show
    # @application = Application.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @application.to_xml(Application.single_options) }
      format.json  { render :json => @application.to_json(Application.single_options) }
    end
  end

  # GET /applications/new
  def new
    @application = Application.new
  end

  # GET /applications/1/edit
  def edit
#    @application = Application.find(params[:id])
  end

  # POST /applications
  # POST /applications.xml
  # POST /applications.json
  def create
    @application = Application.new(params[:application])
    @application.modified_by = current_user

    authorize_app(@application)

    respond_to do |format|
      if @application.save
        format.html { redirect_to(@application, :notice => 'Application was successfully created.') }
        format.xml  { render :xml => @application.to_xml(Application.single_options), :status => :created, :location => @application }
        format.json  { render :json => @application.to_json(Application.single_options), :status => :created, :location => @application }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @application.errors, :status => :unprocessable_entity }
        format.json  { render :json => @application.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /applications/1
  # PUT /applications/1.xml
  # PUT /applications/1.json
  def update
    # @application = Application.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    params[:application] ||= {}
    params[:application][:modified_by] = current_user

    respond_to do |format|
      if @application.update_attributes(params[:application])
        format.html { redirect_to(@application, :notice => 'Application was successfully updated.') }
        format.xml  { render :xml => @application.to_xml(Application.single_options) }
        format.json  { render :json => @application.to_json(Application.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @application.errors, :status => :unprocessable_entity }
        format.json  { render :json => @application.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /applications/1
  # DELETE /applications/1.xml
  # DELETE /applications/1.json
  def destroy
    # @application = Application.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    @application.destroy

    respond_to do |format|
      format.html { redirect_to(applications_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
