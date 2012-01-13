require 'spec_helper'

describe "applications/new.html.erb" do
  before(:each) do
    assign(:application, stub_model(Application,
      :name => "MyString",
      :url => "MyString"
    ).as_new_record)
  end

  it "renders new application form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => applications_path, :method => "post" do
      assert_select "input#application_name", :name => "application[name]"
      assert_select "input#application_url", :name => "application[url]"
    end
  end
end
