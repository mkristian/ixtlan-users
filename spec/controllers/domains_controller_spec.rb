require 'spec_helper'

describe Remote::DomainsController do

  describe "GET last_changes as json" do
    it "should return all domains" do
      request.remote_addr = '127.0.0.1'
      request.env['X-SERVICE-TOKEN'] = 'behappy'
      get :last_changes, :format => :json, :updated_at => "2000-01-01 01:01:01.000000"
      body = JSON.parse(response.body)
      body.should have(1).items
      body.each do |item|
        domain = item["domain"]
        # puts domain.to_yaml 
        domain.should have(3).items
        domain['id'].should_not be_nil
        domain['updated_at'].should_not be_nil
        domain['name'].should_not be_nil
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
