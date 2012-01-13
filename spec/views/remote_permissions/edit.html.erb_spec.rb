require 'spec_helper'

describe "remote_permissions/edit.html.erb" do
  before(:each) do
    @remote_permission = assign(:remote_permission, stub_model(RemotePermission,
      :ip => "MyString",
      :token => "MyString",
      :application => nil
    ))
  end

  it "renders the edit remote_permission form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => remote_permissions_path(@remote_permission), :method => "post" do
      assert_select "input#remote_permission_ip", :name => "remote_permission[ip]"
      assert_select "input#remote_permission_token", :name => "remote_permission[token]"
      assert_select "input#remote_permission_application", :name => "remote_permission[application]"
    end
  end
end
