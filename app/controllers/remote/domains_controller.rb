class Remote::DomainsController < Remote::ApplicationController

  public

  # GET /domains/last_changes
  def last_changes
    @domains = serializer( Domain.all_changed_after( params[:updated_at] ) ).use( :update )
    respond_with @domains
  end

end
