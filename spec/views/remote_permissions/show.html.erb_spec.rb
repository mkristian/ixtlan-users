require 'spec_helper'

describe "remote_permissions/show.html.erb" do
  before(:each) do
    @remote_permission = assign(:remote_permission, stub_model(RemotePermission,
      :ip => "Ip",
      :token => "Token",
      :application => nil
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Ip/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Token/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(//)
  end
end
