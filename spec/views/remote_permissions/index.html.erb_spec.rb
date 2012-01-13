require 'spec_helper'

describe "remote_permissions/index.html.erb" do
  before(:each) do
    assign(:remote_permissions, [
      stub_model(RemotePermission,
        :ip => "Ip",
        :token => "Token",
        :application => nil
      ),
      stub_model(RemotePermission,
        :ip => "Ip",
        :token => "Token",
        :application => nil
      )
    ])
  end

  it "renders a list of remote_permissions" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Ip".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Token".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => nil.to_s, :count => 2
  end
end
