class Remote::ApplicationsController < Remote::ApplicationController

  # GET /applications/last_changes
  def last_changes
    @applications = Application.all_changed_after( params[ :updated_at ] )
    respond_with serializer( @applications ).use( :update )
  end
end
