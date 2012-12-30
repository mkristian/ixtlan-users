require 'spec_helper'
require 'application_serializer'

describe ApplicationSerializer do

  describe "json" do
    subject do
      ApplicationSerializer.new(Application.last)
    end

    it "use single" do
      body = JSON.parse(subject.to_json)
      #puts body.to_yaml
      application = body['application']
      application.should have(9).items
      application['id'].should_not be_nil
      application['name'].should_not be_nil
      application['url'].should_not be_nil
      application['allowed_ip'].should_not be_nil
      application['authentication_token'].should_not be_nil
      application['created_at'].should_not be_nil
      application['updated_at'].should_not be_nil
      application['modified_by'].should_not be_nil
      groups = application['groups']
      groups.should have(1).items
      groups.each do |group|
        group.should have(5).items
        group['id'].should_not be_nil
        group['name'].should_not be_nil
        group['has_regions'].should == false
        group['has_locales'].should == false
        group['has_domains'].should == false
      end
    end


    it "use collection" do
      body = JSON.parse(ApplicationSerializer.new( Application.all ).to_json)
      # puts body.to_yaml
      body.should have(3).items
      application = body.each do |item|
        application = item['application']
        application.should have(4).items
        application['id'].should_not be_nil
        application.key?('url').should == true
        application['name'].should_not be_nil
        application['updated_at'].should_not be_nil
      end
    end

  end

end
