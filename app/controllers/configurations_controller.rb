class ConfigurationsController < ApplicationController
 
  # GET /configurations
  # GET /configurations.xml
  # GET /configurations.json
  def show
    @configuration = Configuration.instance

    # needed to create valid json/xml i.e. with timestamps
    @configuration.save if @configuration.new_record?

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @configuration }
      format.json  { render :json => @configuration }
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
    @configuration = Configuration.instance

    respond_to do |format|
      if @configuration.update_attributes(params[:configuration])
        format.html { redirect_to(configuration_path, :notice => 'Configuration was successfully updated.') }
        format.xml  { render :xml => @configuration }
        format.json  { render :json => @configuration }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @configuration.errors, :status => :unprocessable_entity }
        format.json  { render :json => @configuration.errors, :status => :unprocessable_entity }
      end
    end
  end
end
