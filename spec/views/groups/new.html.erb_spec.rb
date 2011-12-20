require 'spec_helper'

describe "groups/new.html.erb" do
  before(:each) do
    assign(:group, stub_model(Group,
      :name => "MyString",
      :description => "MyText",
      :application => nil,
      :has_regions => false
    ).as_new_record)
  end

  it "renders new group form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form", :action => groups_path, :method => "post" do
      assert_select "input#group_name", :name => "group[name]"
      assert_select "textarea#group_description", :name => "group[description]"
      assert_select "input#group_application", :name => "group[application]"
      assert_select "input#group_has_regions", :name => "group[has_regions]"
    end
  end
end
