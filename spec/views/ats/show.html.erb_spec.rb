require 'spec_helper'

describe "ats/show.html.erb" do
  before(:each) do
    @at = assign(:at, stub_model(At,
      :login => "Login",
      :email => "Email",
      :name => "Name",
      :hashed => "Hashed",
      :hashed2 => "Hashed2",
      :at_token => "At Token"
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Login/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Email/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Name/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Hashed/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Hashed2/)
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/At Token/)
  end
end
