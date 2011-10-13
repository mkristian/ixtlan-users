require 'spec_helper'

describe "configurations/edit.html.erb" do
  before(:each) do
    @configuration = assign(:configuration, stub_model(Configuration,
      :idle_session_timeout => 1,
      :from_email => "MyString",
      :application => nil
    ))
  end

  it "renders the edit configuration form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => configurations_path(@configuration), :method => "post" do
      assert_select "input#configuration_idle_session_timeout", :name => "configuration[idle_session_timeout]"
      assert_select "input#configuration_from_email", :name => "configuration[from_email]"
      assert_select "input#configuration_application", :name => "configuration[application]"
    end
  end
end
