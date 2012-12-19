class AuditsController < LocalController

  before_filter :authorize_root_on_this
  skip_before_filter :authorize

  # GET /audits
  def index
    @audits = Audit.all.reverse

    respond_with serializer( @audits )
  end

  # GET /audits/1
  def show
    @audit = Audit.find(params[:id])

    respond_with serializer( @audit )
  end
end
