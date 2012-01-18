require 'spec_helper'

describe "configurations/show.html.erb" do
  before(:each) do
    @configuration = assign(:configuration, stub_model(Configuration,
      :errors_keep_dumps => 1,
      :errors_base_url => "Errors Base Url",
      :errors_from_email => "Errors From Email",
      :errors_to_emails => "Errors To Emails",
      :idle_session_timeout => 1,
      :audits_keep_logs => 1,
      :from_email => "From Email",
      :profile_url => "Profile Url",
      :ats_url => "Ats Url"
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/1/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Errors Base Url/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Errors From Email/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Errors To Emails/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/1/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/1/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/From Email/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Profile Url/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Ats Url/)
  end
end
