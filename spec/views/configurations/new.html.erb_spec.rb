require 'spec_helper'

describe "configurations/new.html.erb" do
  before(:each) do
    assign(:configuration, stub_model(Configuration,
      :idle_session_timeout => 1,
      :from_email => "MyString",
      :application => nil
    ).as_new_record)
  end

  it "renders new configuration form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => configurations_path, :method => "post" do
      assert_select "input#configuration_idle_session_timeout", :name => "configuration[idle_session_timeout]"
      assert_select "input#configuration_from_email", :name => "configuration[from_email]"
      assert_select "input#configuration_application", :name => "configuration[application]"
    end
  end
end
