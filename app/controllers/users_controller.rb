class UsersController < ApplicationController

  skip_before_filter :authorization

  # GET /users
  # GET /users.xml
  # GET /users.json
  def index
    @users = User.all(:conditions => ["updated_at > ?", params[:updated_at]])

    respond_to do |format|
      format.html # index.html.erb 
      format.xml  { render :xml => @users }
      format.json  { render :json => @users }
    end
  end

  # GET /users/1
  # GET /users/1.xml
  # GET /users/1.json
  def show
    @user = User.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @user }
      format.json  { render :json => @user }
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
    @user = User.new(params[:user])
    pwd = @user.reset_password

    respond_to do |format|
      if @user.save
        UserMailer.send_new_account(@user, pwd).deliver
        format.html { redirect_to(@user, :notice => 'User was successfully created.') }
        format.xml  { render :xml => @user, :status => :created, :location => @user }
        format.json  { render :json => @user, :status => :created, :location => @user }
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
    _params = params[:user] || []
    @user = User.optimistic_find(_params.delete(:updated_at), params[:id])

    if @user.nil?
      @user = User.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      return
    end
    _params.delete(:id)
    _params.delete(:created_at)

    respond_to do |format|
      if @user.update_attributes(_params)
        format.html { redirect_to(@user, :notice => 'User was successfully updated.') }
        format.xml  { render :xml => @user }
        format.json  { render :json => @user }
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
    _params = params[:user] || []
    @user = User.optimistic_find(_params.delete(:updated_at), params[:id])

    if @user.nil?
      @user = User.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      return
    end

    pwd = @user.reset_password

    if @user.save
      UserMailer.send_password_email(@user, pwd)
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
    @user = User.optimistic_find((params[:user]||[]).delete(:updated_at), params[:id])

    if @user.nil?
      @user = User.find(params[:id])
      respond_to do |format|
        format.html { render :action => "edit" }
        format.xml  { render :xml => nil, :status => :conflict }
        format.json  { render :json => nil, :status => :conflict }
      end
      return
    end

    @user.destroy

    respond_to do |format|
      format.html { redirect_to(users_url) }
      format.xml  { head :ok }
      format.json  { head :ok }
    end
  end
end
