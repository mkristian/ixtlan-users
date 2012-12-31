require 'spec_helper'

describe Remote::UsersController do

  describe "GET last_changes as json" do
    it "should return all users" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      get :last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.should have(3).items
      body.each do |item|
        user = item["user"]
        user.size.should >=4 
        user.size.should <=5 
        user['id'].should_not be_nil
        user['updated_at'].should_not be_nil
        user['name'].should_not be_nil
        user['login'].should_not be_nil
      end
    end

    it "should return nothing" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      get :last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.should have(0).items
    end

    it "should create error on wrong IP" do
      request.remote_addr = '1.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      lambda { get :last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000" }.should raise_error
    end

    it "should create error on wrong token" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'something'
      lambda { get :last_changes, :format => :json, :updated_at => "3000-01-01 01:01:01.000000" }.should raise_error
    end
  end

end
