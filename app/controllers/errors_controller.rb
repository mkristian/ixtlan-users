class ErrorsController < ApplicationController

  before_filter :cleanup_params

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:error] || []
    model.delete :id
    model.delete :created_at
    model.delete :updated_at
  end

  public

  # GET /errors
  # GET /errors.xml
  # GET /errors.json
  def index
    @errors = Error.all

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @errors.to_xml(Error.options) }
      format.json  { render :json => @errors.to_json(Error.options) }
    end
  end

  # GET /errors/1
  # GET /errors/1.xml
  # GET /errors/1.json
  def show
    @error = Error.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @error.to_xml(Error.single_options) }
      format.json  { render :json => @error.to_json(Error.single_options) }
    end
  end

  # GET /errors/new
  def new
    @error = Error.new
  end

  # GET /errors/1/edit
  def edit
    @error = Error.find(params[:id])
  end

  # POST /errors
  # POST /errors.xml
  # POST /errors.json
  def create
    @error = Error.new(params[:error])

    respond_to do |format|
      if @error.save
        format.html { redirect_to(@error, :notice => 'Error was successfully created.') }
        format.xml  { render :xml => @error.to_xml(Error.single_options), :status => :created, :location => @error }
        format.json  { render :json => @error.to_json(Error.single_options), :status => :created, :location => @error }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @error.errors, :status => :unprocessable_entity }
        format.json  { render :json => @error.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /errors/1
  # PUT /errors/1.xml
  # PUT /errors/1.json
  def update
    @error = Error.find(params[:id])

    respond_to do |format|
      if @error.update_attributes(params[:error])
        format.html { redirect_to(@error, :notice => 'Error was successfully updated.') }
        format.xml  { render :xml => @error.to_xml(Error.single_options) }
        format.json  { render :json => @error.to_json(Error.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @error.errors, :status => :unprocessable_entity }
        format.json  { render :json => @error.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /errors/1
  # DELETE /errors/1.xml
  # DELETE /errors/1.json
  def destroy
    @error = Error.find(params[:id])

    @error.destroy

    respond_to do |format|
      format.html { redirect_to(errors_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
