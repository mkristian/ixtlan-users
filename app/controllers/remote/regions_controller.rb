class Remote::RegionsController < Remote::ApplicationController

  # GET /regions/last_changes
  def last_changes
    @regions = Region.all_changed_after( params[:updated_at] )
    respond_with serializer( @regions ).use( :update )
  end

end
