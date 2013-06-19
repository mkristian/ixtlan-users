class ConfigurationController < LocalController

  before_filter :authorize_root_on_this
  skip_before_filter :authorize

  # GET /configuration
  def show
    @configuration = ::Configuration.instance

    respond_with serializer( @configuration )
  end


  # PUT /configuration
  def update
    @configuration = ::Configuration.optimistic_find( updated_at,
                                                      ::Configuration.instance.id )

    params[:configuration] ||= {}
    params[:configuration][:modified_by] = current_user

    @configuration.update_attributes( params[:configuration] )

    respond_with serializer( @configuration )
  end
end
