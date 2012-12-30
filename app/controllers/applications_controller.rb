class ApplicationsController < LocalController

#  before_filter :authorize_application, :except => [:index, :create]

#  skip_before_filter :authorize, :except => [:index, :create]

  guard_filter do |groups|
    groups.select do |group|
      group.application == Application.ALL || 
        ( application && group.application == application )
    end
  end

  private

  def authorize_application
    #TODO super(params[:id])
    if params[:id]
      @application = 
        if updated_at
          Application.optimistic_find(updated_at, params[:id])
        else
          Application.find(params[:id])
        end
      authorize_app(@application)
      @application
    end
  end

  def application
    aid = params[ :application_id ] || params[ :id ]
    @application ||= Application.find( aid ) if aid
  public

  # GET /applications
  def index
    @applications = Application.filtered_all( current_user )

    respond_with serializer( @applications )
  end

  # GET /applications/1
  def show
    respond_with serializer( application )
  end

  # POST /applications
  def create
    @application = Application.new( params[:application] )
    @application.modified_by = current_user

#    authorize_app(@application)

    @application.save
    
    respond_with serializer( @application )
  end

  # PUT /applications/1
  def update
    params[:application][:modified_by] = current_user

    application.update_attributes( params[:application] )

    respond_with serializer( application )
  end

  # DELETE /applications/1
  def destroy
    application.destroy

    head :ok
  end

  def group_create
    group = application.group_create( current_user, params[ :group ] )

    # for audit log
    @application = group

    respond_with serializer( group )
  end

  def group_update
    group = application.group_update( current_user, 
                                      updated_at,
                                      param[ :id ], 
                                      params[ :group ] )

    # for audit log
    @application = group

    respond_with serializer( group )
  end

  def group_destroy
    group = application.group_delete( updated_at,
                                      param[ :id ] )

    # for audit log
    @application = group

    head :ok
  end
end
