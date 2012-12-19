class RegionsController < LocalController

  # GET /regions
  def index
    @regions = Region.all

    respond_with serializer( @regions )
  end

  # GET /regions/1
  def show
    @region = Region.find(params[:id])

    respond_with serializer( @region )
  end

  # POST /regions
  def create
    @region = Region.new( params[:region] )
    @region.modified_by = current_user

    @region.save

    respond_with serializer( @region )
  end

  # PUT /regions/1
  def update
    @region = Region.optimistic_find( updated_at, params[:id] )

    params[:region][:modified_by] = current_user

    @region.update_attributes( params[:region] )

    respond_with serializer( @region )
  end

  # DELETE /regions/1
  def destroy
    @region = Region.optimistic_find( updated_at, params[:id] )

    @region.destroy

    head :ok
  end
end
