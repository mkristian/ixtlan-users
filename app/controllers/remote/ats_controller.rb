class Remote::AtsController < Remote::ApplicationController

  public

  # GET /ats/last_changes
  def last_changes
    @ats = serializer( User.all_changed_after( params[ :updated_at ], true ) ).use( :at_update )
    respond_with @ats
  end
end
