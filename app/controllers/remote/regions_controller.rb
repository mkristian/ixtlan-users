class Remote::RegionsController < Remote::ApplicationController

  public

  # GET /regions/last_changes
  def last_changes
    @regions = serializer( Region.all_changed_after( params[:updated_at] ) ).use( :update )
    respond_with @regions
  end

end
