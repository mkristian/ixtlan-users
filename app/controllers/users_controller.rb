class UsersController < ApplicationController

  before_filter :cleanup_params
  before_filter :ip_restriction, :only => :last_changes
  skip_before_filter :authorization,  :only => :last_changes

  private

  def cleanup_params
    # compensate the shortcoming of the incoming json/xml
    model = params[:user] || []
    model.delete :id
    model.delete :created_at
    params[:updated_at] ||= model.delete :updated_at
  end

  def stale?
    if @user.nil?
      @user = User.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      true
    end
  end

  public

  # GET /users/last_changes.xml
  # GET /users/last_changes.json
  def last_changes
    @users = User.all_changed_after(params[:updated_at])

    respond_to do |format|
      format.xml  { render :xml => @users.to_xml(User.update_options) }
      format.json  { render :json => @users.to_json(User.update_options) }
    end
  end

  # GET /users
  # GET /users.xml
  # GET /users.json
  def index
    @users = User.all

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @users.to_xml(User.options) }
      format.json  { render :json => @users.to_json(User.options) }
    end
  end

  # GET /users/1
  # GET /users/1.xml
  # GET /users/1.json
  def show
    @user = User.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @user.to_xml(User.single_options) }
      format.json  { render :json => @user.to_json(User.single_options) }
    end
  end

  # GET /users/new
  def new
    @user = User.new
  end

  # GET /users/1/edit
  def edit
    @user = User.find(params[:id])
  end

  # POST /users
  # POST /users.xml
  # POST /users.json
  def create
    # delete groups but keep group_ids
    (params[:user] || []).delete(:groups)

    @user = User.new(params[:user])
    @user.modified_by = current_user

    respond_to do |format|
      if @user.reset_password_and_save
        format.html { redirect_to(@user, :notice => 'User was successfully created.') }
        format.xml  { render :xml => @user.to_xml(User.single_options), :status => :created, :location => @user }
        format.json  { render :json => @user.to_json(User.single_options), :status => :created, :location => @user }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @user.errors, :status => :unprocessable_entity }
        format.json  { render :json => @user.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /users/1
  # PUT /users/1.xml
  # PUT /users/1.json
  def update
    @user = User.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    params[:user] ||= {}
    params[:user][:modified_by] = current_user
    
    # delete groups but keep group_ids
    params[:user].delete(:groups)

    respond_to do |format|
      if @user.update_attributes(params[:user])
        format.html { redirect_to(@user, :notice => 'User was successfully updated.') }
        format.xml  { render :xml => @user.to_xml(User.single_options) }
        format.json  { render :json => @user.to_json(User.single_options) }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @user.errors, :status => :unprocessable_entity }
        format.json  { render :json => @user.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /users/1/reset_password.xml
  # PUT /users/1/reset_password.json
  def reset_password
    @user = User.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    # @user.reset_password

    if @user.reset_password_and_save
      #UserMailer.send_password(@user)
      respond_to do |format|
        format.html { redirect_to(@user, :notice => 'Password reset was successful.') }
        format.xml  { head :ok }
        format.json  {  head :ok }
      end
    else
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => @user.errors, :status => :unprocessable_entity }
        format.json  { render :json => @user.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /users/1
  # DELETE /users/1.xml
  # DELETE /users/1.json
  def destroy
    @user = User.optimistic_find(params[:updated_at], params[:id])

    return if stale?

    @user.destroy

    respond_to do |format|
      format.html { redirect_to(users_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
