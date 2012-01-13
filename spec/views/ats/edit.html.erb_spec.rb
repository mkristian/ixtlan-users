require 'spec_helper'

describe "ats/edit.html.erb" do
  before(:each) do
    @at = assign(:at, stub_model(At,
      :login => "MyString",
      :email => "MyString",
      :name => "MyString",
      :hashed => "MyString",
      :hashed2 => "MyString",
      :at_token => "MyString"
    ))
  end

  it "renders the edit at form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => ats_path(@at), :method => "post" do
      assert_select "input#at_login", :name => "at[login]"
      assert_select "input#at_email", :name => "at[email]"
      assert_select "input#at_name", :name => "at[name]"
      assert_select "input#at_hashed", :name => "at[hashed]"
      assert_select "input#at_hashed2", :name => "at[hashed2]"
      assert_select "input#at_at_token", :name => "at[at_token]"
    end
  end
end
