require 'spec_helper'
require File.join(Rails.root, 'app', 'serializers', 'user_serializer')

describe UserSerializer do

  describe "json" do
    subject do
      UserSerializer.new(User.first)
    end

    it "use single" do
      body = JSON.parse(subject.to_json)
      user = body['user']
      user.should have(9).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user['login'].should_not be_nil
      user['email'].should_not be_nil
      user['groups'].should_not be_nil
      user['created_at'].should_not be_nil
      user['updated_at'].should_not be_nil
      user['modified_by'].should_not be_nil
      user.key?('at_token').should == true
    end

    it "use setup" do
      body = JSON.parse( subject.use( :for_app ).to_json )
      user = body['user']
      #puts user.to_yaml
      user.should have(6).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user['login'].should_not be_nil
      user['email'].should_not be_nil
      user['groups'].should_not be_nil
      user['updated_at'].should_not be_nil
    end

    it "use update" do
      body = JSON.parse(subject.use(:update).to_json)
      user = body['user']
      user.should have(4).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user['login'].should_not be_nil
      user['updated_at'].should_not be_nil
    end

    it "use at_update" do
      body = JSON.parse(subject.use(:at_update).to_json)
      user = body['at']
      user.should have(4).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user.key?('at_token').should == true
      user['updated_at'].should_not be_nil
    end

    it "use authenticate" do
      body = JSON.parse(subject.use(:authenticate).to_json)
      user = body['user']
      user.should have(5).items
      user['id'].should_not be_nil
      user['login'].should_not be_nil
      user['name'].should_not be_nil
      user['groups'].should_not be_nil
      user['applications'].should_not be_nil
    end

    it "use collection" do
      body = JSON.parse(UserSerializer.new( User.all ).to_json)
      # puts body.to_yaml
      body.should have(3).items
      user = body.each do |item|
        user = item['user']
        user.should have(8).items
        user['id'].should_not be_nil
        user['login'].should_not be_nil
        user['name'].should_not be_nil
        user['email'].should_not be_nil
        user['updated_at'].should_not be_nil
        user.key?('at_token').should == true
        user['group_ids'].should_not be_nil
        user['application_ids'].should_not be_nil
      end
    end

  end

end
