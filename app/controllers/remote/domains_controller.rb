class Remote::DomainsController < Remote::ApplicationController

  # GET /domains/last_changes
  def last_changes
    @domains = Domain.all_changed_after( params[:updated_at] )
    respond_with serializer( @domains ).use( :update )
  end

end
