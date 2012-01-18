class AtsController < ApplicationController

#  before_filter :cleanup_params
  before_filter :remote_permission, :only => :last_changes
  skip_before_filter :authorize,  :only => :last_changes

  # private

  # def cleanup_params
  #   # compensate the shortcoming of the incoming json/xml
  #   model = params[:at] || []
  #   model.delete :id
  #   model.delete :created_at
  #   params[:updated_at] = model.delete :updated_at
  # end

  # def stale?
  #   if @at.nil?
  #     @at = At.find(params[:id])
  #     respond_to do |format|
  #       format.html { render :action => "edit" }
  #       format.xml  { render :xml => nil, :status => :conflict }
  #       format.json  { render :json => nil, :status => :conflict }
  #     end
  #     true
  #   end
  # end

  # public

  # GET /ats/last_changes.xml
  # GET /ats/last_changes.json
  def last_changes
    @users = User.all_changed_after(params[:updated_at], true)

    respond_to do |format|
      format.xml  { render :xml => @users.to_xml(User.update_options) }
      format.json  { render :json => @users.to_json(User.update_options) }
    end
  end

  # # GET /ats
  # # GET /ats.xml
  # # GET /ats.json
  # def index
  #   @ats = At.joins(:groups).where('groups.id' => Group.AT.id)

  #   respond_to do |format|
  #     format.html # index.html.erb 
  #     format.xml  { render :xml => @ats.to_xml(At.options) }
  #     format.json  { render :json => @ats.to_json(At.options) }
  #   end
  # end

  # # GET /ats/1
  # # GET /ats/1.xml
  # # GET /ats/1.json
  # def show
  #   @at = At.find(params[:id])

  #   respond_to do |format|
  #     format.html # show.html.erb
  #     format.xml  { render :xml => @at.to_xml(At.single_options) }
  #     format.json  { render :json => @at.to_json(At.single_options) }
  #   end
  # end

  # # GET /ats/new
  # def new
  #   @at = At.new
  # end

  # # GET /ats/1/edit
  # def edit
  #   @at = At.find(params[:id])
  # end

  # # POST /ats
  # # POST /ats.xml
  # # POST /ats.json
  # def create
  #   @at = At.new(params[:at])
  #   @at.modified_by = current_user

  #   respond_to do |format|
  #     if @at.save
  #       format.html { redirect_to(@at, :notice => 'At was successfully created.') }
  #       format.xml  { render :xml => @at.to_xml(At.single_options), :status => :created, :location => @at }
  #       format.json  { render :json => @at.to_json(At.single_options), :status => :created, :location => @at }
  #     else
  #       format.html { render :action => "new" }
  #       format.xml  { render :xml => @at.errors, :status => :unprocessable_entity }
  #       format.json  { render :json => @at.errors, :status => :unprocessable_entity }
  #     end
  #   end
  # end

  # # PUT /ats/1
  # # PUT /ats/1.xml
  # # PUT /ats/1.json
  # def update
  #   @at = At.optimistic_find(params[:updated_at], params[:id])

  #   return if stale?

  #   params[:at] ||= {}
  #   params[:at][:modified_by] = current_user

  #   respond_to do |format|
  #     if @at.update_attributes(params[:at])
  #       format.html { redirect_to(@at, :notice => 'At was successfully updated.') }
  #       format.xml  { render :xml => @at.to_xml(At.single_options) }
  #       format.json  { render :json => @at.to_json(At.single_options) }
  #     else
  #       format.html { render :action => "edit" }
  #       format.xml  { render :xml => @at.errors, :status => :unprocessable_entity }
  #       format.json  { render :json => @at.errors, :status => :unprocessable_entity }
  #     end
  #   end
  # end

  # # DELETE /ats/1
  # # DELETE /ats/1.xml
  # # DELETE /ats/1.json
  # def destroy
  #   @at = At.optimistic_find(params[:updated_at], params[:id])

  #   return if stale?

  #   @at.destroy

  #   respond_to do |format|
  #     format.html { redirect_to(ats_url) }
  #     format.xml  { head :ok }
  #     format.json  { head :ok }
  #   end
  # end
end
