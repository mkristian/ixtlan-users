require 'spec_helper'

describe Remote::AuthenticationsController do

  describe "create authentication" do

    subject do
      g = Group.where( :application_id => 3,
                       :name => 'root' )[0]
      u = User.find_by_login( 'root' )
      u.groups << g
      u
    end

    before do
      @pwd = subject.reset_password_and_save
    end

    it "should return ok" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      post :create, :format => :json, :authentication => {:login => "root", :password => @pwd }
      response.status.should == 201
      body = JSON.parse(response.body)
      # puts body.to_yaml
      body.should have(1).items
      user = body["user"]
      user.should have(5).items
      user['id'].should == 2
      user['name'].should == 'Root'
      user['login'].should == 'root'
      user['groups'].should have(1).items
      user['groups'].each do |group|
        group.should have(6).items
        group['id'].should_not be_nil
        group['name'].should == 'root'
        group['regions'].should be_nil
        group['locales'].should be_nil
        group['domains'].should be_nil
        group['application'].should have(2).items
      end
      user['applications'].should have(2).items
      user['applications'].each do |app|
        app.should have(3).items
        app['id'].should_not be_nil
        app['name'].should_not be_nil
        app['url'].should_not be_nil
      end
    end

    it "should return not authorized" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'

      post :create, :format => :json, :authentication => {:login => "root"}
      response.status.should == 401

      post :create, :format => :json, :authentication => {:login => "root", :password => 'bla'}
      response.status.should == 401

      post :create, :format => :json, :authentication => {:password => @pwd}
      response.status.should == 401
    end

    it "should create error on wrong IP" do
      request.remote_addr = '1.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      lambda { post :create, :format => :json, :authentication => {:login => "root", :password => @pwd } }.should raise_error
    end

    it "should create error on wrong token" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { post :create, :format => :json, :authentication => {:login => "root", :password => @pwd } }.should raise_error
    end
  end

  describe "reset password" do
    it "should return ok" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      get :reset_password, :format => :json, :authentication => {:login => "root"}
      response.status.should == 200
      response.body.should == 'password sent'
    end

    it "should return not found" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      get :reset_password, :format => :json, :authentication => {:login => "notauser"}
      response.status.should == 404
    end

    it "should create error on wrong IP" do
      request.remote_addr = '1.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      lambda { get :reset_password, :format => :json, :authentication => {:login => "root"} }.should raise_error
    end

    it "should create error on wrong token" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { get :reset_password, :format => :json, :authentication => {:login => "root"} }.should raise_error
    end
  end

end
