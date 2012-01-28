class RegionsController < ApplicationController

  before_filter :cleanup_params
  before_filter :remote_permission, :only => :last_changes
  skip_before_filter :authorize,  :only => :last_changes

  private

  # TODO why needed for rspecs 
  def authorize
    super unless params[:action] == "last_changes"
  end

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:region] || []
    model.delete :id
    model.delete :created_at
    params[:updated_at] ||= model.delete :updated_at
  end

  def stale?
    if @region.nil?
      @region = Region.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      true
    end
  end

  public

  # GET /regions/last_changes.xml
  # GET /regions/last_changes.json
  def last_changes
    @regions = Region.all_changed_after(params[:updated_at])

    respond_to do |format|
      format.xml  { render :xml => @regions.to_xml(Region.update_options) }
      format.json  { render :json => @regions.to_json(Region.update_options) }
    end
  end

  # GET /regions
  # GET /regions.xml
  # GET /regions.json
  def index
    @regions = Region.all

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @regions.to_xml(Region.options) }
      format.json  { render :json => @regions.to_json(Region.options) }
    end
  end

  # GET /regions/1
  # GET /regions/1.xml
  # GET /regions/1.json
  def show
    @region = Region.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @region.to_xml(Region.single_options) }
      format.json  { render :json => @region.to_json(Region.single_options) }
    end
  end

  # GET /regions/new
  def new
    @region = Region.new
  end

  # GET /regions/1/edit
  def edit
    @region = Region.find(params[:id])
  end

  # POST /regions
  # POST /regions.xml
  # POST /regions.json
  def create
    @region = Region.new(params[:region])
    @region.modified_by = current_user

    respond_to do |format|
      if @region.save
        format.html { redirect_to(@region, :notice => 'Region was successfully created.') }
        format.xml  { render :xml => @region.to_xml(Region.single_options), :status => :created, :location => @region }
        format.json  { render :json => @region.to_json(Region.single_options), :status => :created, :location => @region }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @region.errors, :status => :unprocessable_entity }
        format.json  { render :json => @region.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /regions/1
  # PUT /regions/1.xml
  # PUT /regions/1.json
  def update
    @region = Region.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    params[:region] ||= {}
    params[:region][:modified_by] = current_user

    respond_to do |format|
      if @region.update_attributes(params[:region])
        format.html { redirect_to(@region, :notice => 'Region was successfully updated.') }
        format.xml  { render :xml => @region.to_xml(Region.single_options) }
        format.json  { render :json => @region.to_json(Region.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @region.errors, :status => :unprocessable_entity }
        format.json  { render :json => @region.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /regions/1
  # DELETE /regions/1.xml
  # DELETE /regions/1.json
  def destroy
    @region = Region.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    @region.destroy

    respond_to do |format|
      format.html { redirect_to(regions_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
