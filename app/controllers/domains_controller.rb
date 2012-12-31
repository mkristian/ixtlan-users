class DomainsController < LocalController

  # GET /domains
  def index
    @domains = Domain.all

    respond_with serializer( @domains )
  end

  # GET /domains/1
  def show
    @domain = Domain.find(params[:id])

    respond_with serializer( @domain )
  end

  # POST /domains
  def create
    @domain = Domain.new( params[:domain] )
    @domain.modified_by = current_user

    @domain.save

    respond_with serializer( @domain )
  end

  # PUT /domains/1
  def update
    @domain = Domain.optimistic_find( updated_at, params[:id] )

    @domain.attributes = params[ :domain ]
    if @domain.changed?
      @domain.modified_by = current_user 
      @domain.save
    end

    respond_with serializer( @domain )
  end

  # DELETE /domains/1
  def destroy 
    @domain = Domain.optimistic_find( updated_at, params[:id] )

    @domain.destroy

    head :ok
  end
end
