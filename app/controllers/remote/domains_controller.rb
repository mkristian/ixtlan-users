class Remote::DomainsController < Remote::ApplicationController

  # GET /domains/last_changes
  def last_changes
    @domains = Domain.all_changed_after( params[:updated_at] )
    respond_with serializer( @domains ).use( :update )
  end

  # GET /domains/last_changes_of_app
  def last_changes_of_app
    @domains = Domain.all_changed_after_of_app( params[ :updated_at ], remote_permission )
    respond_with serializer( @domains ).use( :update )
  end

end
