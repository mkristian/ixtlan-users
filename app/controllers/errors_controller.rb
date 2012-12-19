class ErrorsController < LocalController

  before_filter :authorize_root_on_this
  skip_before_filter :authorize

  # GET /errors
  def index
    @errors = Error.all

    respond_with serializer( @errors )
  end

  # GET /errors/1
  def show
    @error = Error.find( params[:id] )

    respond_with serializer( @error )
  end

end
