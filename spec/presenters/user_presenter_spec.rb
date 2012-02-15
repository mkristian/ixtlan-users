require 'spec_helper'
require File.join(Rails.root, 'app', 'presenters', 'user_presenter')

describe UserPresenter do

  describe "json" do
    subject do
      UserPresenter.new(User.first)
    end

    it "use default" do
      body = JSON.parse(subject.to_json)
      user = body['user']
      user.should have(6).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user['login'].should_not be_nil
      user['applications'].should_not be_nil
      user.key?('at_token').should == true
    end

    it "use single" do
      body = JSON.parse(subject.single.to_json)
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

    it "use remote_update" do
      body = JSON.parse(subject.remote_update.to_json)
      user = body['user']
      user.should have(4).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user['login'].should_not be_nil
      user['updated_at'].should_not be_nil
    end

    it "use at_remote_update" do
      body = JSON.parse(subject.at_remote_update.to_json)
      user = body['at']
      user.should have(4).items
      user['id'].should_not be_nil
      user['name'].should_not be_nil
      user.key?('at_token').should == true
      user['updated_at'].should_not be_nil
    end

    it "use authenticate" do
      body = JSON.parse(subject.authenticate.to_json)
      user = body['user']
      user.should have(5).items
      user['id'].should_not be_nil
      user['login'].should_not be_nil
      user['name'].should_not be_nil
      user['groups'].should_not be_nil
      user['applications'].should_not be_nil
    end

    it "use collection" do
      body = JSON.parse(subject.collection.to_json)
      user = body['user']
      user.should have(8).items
      user['id'].should_not be_nil
      user['login'].should_not be_nil
      user['name'].should_not be_nil
      user['email'].should_not be_nil
      user['group_ids'].should_not be_nil
      user['updated_at'].should_not be_nil
      user['application_ids'].should_not be_nil
      user.key?('at_token').should == true
    end

  end

end
