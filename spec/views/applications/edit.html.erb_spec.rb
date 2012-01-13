require 'spec_helper'

describe "applications/edit.html.erb" do
  before(:each) do
    @application = assign(:application, stub_model(Application,
      :name => "MyString",
      :url => "MyString"
    ))
  end

  it "renders the edit application form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => applications_path(@application), :method => "post" do
      assert_select "input#application_name", :name => "application[name]"
      assert_select "input#application_url", :name => "application[url]"
    end
  end
end
