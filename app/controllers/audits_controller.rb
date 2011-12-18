class AuditsController < ApplicationController

  before_filter :cleanup_params

  before_filter :authorize_root_on_this
  skip_before_filter :authorize

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:audit] || []
    model.delete :id
    model.delete :created_at
    model.delete :updated_at
  end

  public

  # GET /audits
  # GET /audits.xml
  # GET /audits.json
  def index
    @audits = Audit.all.reverse

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @audits.to_xml(Audit.options) }
      format.json  { render :json => @audits.to_json(Audit.options) }
    end
  end

  # GET /audits/1
  # GET /audits/1.xml
  # GET /audits/1.json
  def show
    @audit = Audit.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @audit.to_xml(Audit.single_options) }
      format.json  { render :json => @audit.to_json(Audit.single_options) }
    end
  end

  # GET /audits/new
  def new
    @audit = Audit.new
  end

  # GET /audits/1/edit
  def edit
    @audit = Audit.find(params[:id])
  end

  # POST /audits
  # POST /audits.xml
  # POST /audits.json
  def create
    @audit = Audit.new(params[:audit])

    respond_to do |format|
      if @audit.save
        format.html { redirect_to(@audit, :notice => 'Audit was successfully created.') }
        format.xml  { render :xml => @audit.to_xml(Audit.single_options), :status => :created, :location => @audit }
        format.json  { render :json => @audit.to_json(Audit.single_options), :status => :created, :location => @audit }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @audit.errors, :status => :unprocessable_entity }
        format.json  { render :json => @audit.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /audits/1
  # PUT /audits/1.xml
  # PUT /audits/1.json
  def update
    @audit = Audit.find(params[:id])

    respond_to do |format|
      if @audit.update_attributes(params[:audit])
        format.html { redirect_to(@audit, :notice => 'Audit was successfully updated.') }
        format.xml  { render :xml => @audit.to_xml(Audit.single_options) }
        format.json  { render :json => @audit.to_json(Audit.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @audit.errors, :status => :unprocessable_entity }
        format.json  { render :json => @audit.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /audits/1
  # DELETE /audits/1.xml
  # DELETE /audits/1.json
  def destroy
    @audit = Audit.find(params[:id])

    @audit.destroy

    respond_to do |format|
      format.html { redirect_to(audits_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
