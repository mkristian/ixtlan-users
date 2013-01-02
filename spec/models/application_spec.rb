require 'spec_helper'

describe Application do

  subject { Application.last }

  let(:current_user) { User.first }

  it 'should have applications wth groups' do
    apps = Application.all
    (apps.size > 1).should be_true
    apps.each {|a| (a.groups.size > 0).should be_true }
  end

  it 'should create/update/delete a group for an app' do
    size = subject.groups.size
    g = subject.group_create( current_user, :name => 'testing' )
    subject.groups.reload.size.should == size + 1
    g.persisted?.should be_true

    subject.group_update( current_user, g.updated_at, g.id, :name=> 'testing_new' )
    g.reload.name.should == 'testing_new'

    subject.group_delete( g.updated_at, g.id )
    subject.groups.reload.each { |g| g.name.should_not == 'testing_new' }
    subject.groups.size.should == size
  end
  
  it 'not updated group from other application' do
    group = Group.first
    subject.groups.each { |g| (g.id != group.id).should be_true }
    lambda{ subject.group_update( group.updated_at, group.id, current_user, :name => 'bla' ) }.should raise_error
  end

  it 'not delete group from other application' do
    group = Group.first
    subject.groups.each { |g| (g.id != group.id).should be_true }
    lambda{ subject.group_delete( group.updated_at, group.id ) }.should raise_error
  end

  it 'create default groups for new application' do
    app = Application.create( :name => 'testing', 
                              :url => "http://localhost.net",
                              :allowed_ip => '1.2.3.4', 
                              :authentication_token => '123',
                              :modified_by => current_user )
    app.groups.collect{ |g| g.name }.should == ["root", "translator"]
  end
end
