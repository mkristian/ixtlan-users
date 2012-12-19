class GroupsController < LocalController

  before_filter :authorize_application, :except => [:index]
  skip_before_filter :authorize, :except => [:index]

  # GET /groups
  def index
    @groups = Group.filtered_all( current_user )

    respond_with serializer( @groups )
  end

  # GET /groups/1
  def show
    @group = Group.filtered_find( params[:id], @application )

    respond_with serializer( @group )
  end

  # POST /groups
  def create
    @group = Group.new( params[:group] )
    @group.modified_by = current_user
    @group.application = @application

    respond_with serializer( @group )
  end

  # PUT /groups/1
  def update
    @group = Group.filtered_optimistic_find( updated_at,
                                             params[:id],
                                             @application )

    params[:group][:modified_by] = current_user
    
    authorize_application_param( params[:group] )

    @group.update_attributes( params[:group] )

    respond_with serializer( @group )
  end

  # DELETE /groups/1
  def destroy
    @group = Group.filtered_optimistic_find( updated_at, 
                                             params[:id], 
                                             @application )

    @group.destroy

    head :ok
  end
end
