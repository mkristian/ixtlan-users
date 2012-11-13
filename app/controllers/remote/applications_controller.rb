class Remote::ApplicationsController < Remote::ApplicationController

  public

  # GET /applications/last_changes
  def last_changes
    @applications = serializer( Application.all_changed_after( params[ :updated_at ] ) ).use( :update )
    respond_with @applications
  end
end
