class GroupsController < ApplicationController

  before_filter :cleanup_params

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:group] || []
    model.delete :id
    model.delete :created_at
    model.delete :updated_at
  end

  public

  # GET /groups
  # GET /groups.xml
  # GET /groups.json
  def index
    @groups = Group.all

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @groups }
      format.json  { render :json => @groups }
    end
  end

  # GET /groups/1
  # GET /groups/1.xml
  # GET /groups/1.json
  def show
    @group = Group.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @group }
      format.json  { render :json => @group }
    end
  end

  # GET /groups/new
  def new
    @group = Group.new
  end

  # GET /groups/1/edit
  def edit
    @group = Group.find(params[:id])
  end

  # POST /groups
  # POST /groups.xml
  # POST /groups.json
  def create
    @group = Group.new(params[:group])
    @group.modified_by = current_user

    respond_to do |format|
      if @group.save
        format.html { redirect_to(@group, :notice => 'Group was successfully created.') }
        format.xml  { render :xml => @group, :status => :created, :location => @group }
        format.json  { render :json => @group, :status => :created, :location => @group }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @group.errors, :status => :unprocessable_entity }
        format.json  { render :json => @group.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /groups/1
  # PUT /groups/1.xml
  # PUT /groups/1.json
  def update
    @group = Group.find(params[:id])
    params[:group] ||= {}
    params[:group][:modified_by] = current_user

    respond_to do |format|
      if @group.update_attributes(params[:group])
        format.html { redirect_to(@group, :notice => 'Group was successfully updated.') }
        format.xml  { render :xml => @group }
        format.json  { render :json => @group }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @group.errors, :status => :unprocessable_entity }
        format.json  { render :json => @group.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /groups/1
  # DELETE /groups/1.xml
  # DELETE /groups/1.json
  def destroy
    @group = Group.find(params[:id])

    @group.destroy

    respond_to do |format|
      format.html { redirect_to(groups_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
