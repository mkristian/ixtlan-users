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

  def setup
    @domain = Domain.where( :name => params[ 'name' ] ).first ||  Domain.new( :name => params[ 'name' ] )
    @domain.modified_by = User.system_user if @domain.new_record?
    @domain.save

    respond_with serializer( @domain ).use( :update )
  end
end
