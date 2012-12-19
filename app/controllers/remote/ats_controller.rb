class Remote::AtsController < Remote::ApplicationController

  # GET /ats/last_changes
  def last_changes
    @ats = User.all_changed_after( params[ :updated_at ], true )
    respond_with serializer( @ats ).use( :at_update )
  end
end
