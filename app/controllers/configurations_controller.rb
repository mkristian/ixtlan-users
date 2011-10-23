class ConfigurationsController < ApplicationController
 
  before_filter :cleanup_params

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:configuration] || []
    model.delete :created_at
    params[:updated_at] = model.delete :updated_at
  end

  def stale?
    if @configuration.nil?
      @configuration = Configuration.instance
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      true
    end
  end

  public

  # GET /configurations
  # GET /configurations.xml
  # GET /configurations.json
  def show
    @configuration = Configuration.instance

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @configuration.to_xml(Configuration.single_options) }
      format.json  { render :json => @configuration.to_json(Configuration.single_options) }
    end
  end

  # GET /configurations/edit
  def edit
    @configuration = Configuration.instance
  end

  # PUT /configurations
  # PUT /configurations.xml
  # PUT /configurations.json
  def update
    @configuration = Configuration.optimistic_find(params[:updated_at], Configuration.instance.id)

    return if stale?

    params[:configuration] ||= {}
    params[:configuration][:modified_by] = current_user

    respond_to do |format|
      if @configuration.update_attributes(params[:configuration])
        format.html { redirect_to(configuration_path, :notice => 'Configuration was successfully updated.') }
        format.xml  { render :xml => @configuration.to_xml(Configuration.single_options) }
        format.json  { render :json => @configuration.to_json(Configuration.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @configuration.errors, :status => :unprocessable_entity }
        format.json  { render :json => @configuration.errors, :status => :unprocessable_entity }
      end
    end
  end
end
