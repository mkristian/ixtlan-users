class LocalesController < LocalController

  def index
    @locales = Locale.all

    respond_with serializer( @locales )
  end

  # GET /locales/1
  def show
    @locale = Locale.find(params[:id])

    respond_with serializer( @locale )
  end
end
