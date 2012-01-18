require 'spec_helper'

describe "configurations/edit.html.erb" do
  before(:each) do
    @configuration = assign(:configuration, stub_model(Configuration,
      :errors_keep_dumps => 1,
      :errors_base_url => "MyString",
      :errors_from_email => "MyString",
      :errors_to_emails => "MyString",
      :idle_session_timeout => 1,
      :audits_keep_logs => 1,
      :from_email => "MyString",
      :profile_url => "MyString",
      :ats_url => "MyString"
    ))
  end

  it "renders the edit configuration form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => configurations_path(@configuration), :method => "post" do
      assert_select "input#configuration_errors_keep_dumps", :name => "configuration[errors_keep_dumps]"
      assert_select "input#configuration_errors_base_url", :name => "configuration[errors_base_url]"
      assert_select "input#configuration_errors_from_email", :name => "configuration[errors_from_email]"
      assert_select "input#configuration_errors_to_emails", :name => "configuration[errors_to_emails]"
      assert_select "input#configuration_idle_session_timeout", :name => "configuration[idle_session_timeout]"
      assert_select "input#configuration_audits_keep_logs", :name => "configuration[audits_keep_logs]"
      assert_select "input#configuration_from_email", :name => "configuration[from_email]"
      assert_select "input#configuration_profile_url", :name => "configuration[profile_url]"
      assert_select "input#configuration_ats_url", :name => "configuration[ats_url]"
    end
  end
end
