class AtsController < ApplicationController

  before_filter :remote_permission, :only => :last_changes
  skip_before_filter :authorize,  :only => :last_changes

  private

  # TODO why needed for rspecs 
  def authorize
    super unless params[:action] == "last_changes"
  end

  public

  # GET /ats/last_changes.xml
  # GET /ats/last_changes.json
  def last_changes
    @ats = User.all_changed_after(params[:updated_at], true)

    respond_to do |format|
      format.xml  { render :xml => @ats.to_xml(User.at_update_options) }
      format.json  { render :json => @ats.to_json(User.at_update_options) }
    end
  end
end
