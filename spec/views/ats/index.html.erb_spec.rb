require 'spec_helper'

describe "ats/index.html.erb" do
  before(:each) do
    assign(:ats, [
      stub_model(At,
        :login => "Login",
        :email => "Email",
        :name => "Name",
        :hashed => "Hashed",
        :hashed2 => "Hashed2",
        :at_token => "At Token"
      ),
      stub_model(At,
        :login => "Login",
        :email => "Email",
        :name => "Name",
        :hashed => "Hashed",
        :hashed2 => "Hashed2",
        :at_token => "At Token"
      )
    ])
  end

  it "renders a list of ats" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Login".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Email".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Name".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Hashed".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Hashed2".to_s, :count => 2
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "At Token".to_s, :count => 2
  end
end
