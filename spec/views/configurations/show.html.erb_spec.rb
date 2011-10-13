require 'spec_helper'

describe "configurations/show.html.erb" do
  before(:each) do
    @configuration = assign(:configuration, stub_model(Configuration,
      :idle_session_timeout => 1,
      :from_email => "From Email",
      :application => nil
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/1/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/From Email/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(//)
  end
end
