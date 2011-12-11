require 'spec_helper'
require 'ixtlan/users/manager'

describe Ixtlan::Users::Manager do

  before do
    u = User.first
    @user = User.new :login => 'someone', :name => 'me', :email => 'me@example.com'
    if u.nil?
      @user.id = 1
      @user.save
      u = @user
    end
    @user.modified_by = u
    @user.save
    
    this = Application.THIS
    this.modified_by = User.first
    this.save
    
    all = Application.ALL
    all.modified_by = User.first
    all.save
    
    root = Group.ROOT
    root.modified_by = User.first
    root.save

    @a1 = Application.find_by_name("spec-app1") || Application.create(:name => "spec-app1", :modified_by => User.first)
    @a2 = Application.find_by_name("spec-app2") || Application.create(:name => "spec-app2", :modified_by => User.first, :url => "http://example.com/app")
    @a3 = Application.find_by_name("spec-app3") || Application.create(:name => "spec-app3", :modified_by => User.first, :url => "http://example.com/app")
    
    @g1 = Group.find_by_name("spec1") || Group.create(:name => "spec1", :modified_by => User.first, :application => @a1)
    @g2 = Group.find_by_name("spec2") || Group.create(:name => "spec2", :modified_by => User.first, :application => @a2)
    @user.groups << @g1
    @user.groups << @g2
    @user.save

    @no_user = User.find_by_login("no") || User.create(:login => "no", :modified_by => User.first)
    @current_user = User.find_by_login("current") || User.create(:login => "current", :modified_by => User.first)
    @current_user.groups << @g1
    @current_user.save
    ApplicationsGroupsUser.create(:user_id => @user.id,
                                  :group_id => @g1.id,
                                  :application_id => @a1.id)
    ApplicationsGroupsUser.create(:user_id => @current_user.id,
                                  :group_id => @g1.id,
                                  :application_id => @a2.id)
    ApplicationsGroupsUser.create(:user_id => @current_user.id,
                                  :group_id => @g1.id,
                                  :application_id => @a3.id)

    @root_user = User.find_by_login("root") || User.create(:login => "root", :modified_by => User.first)
    @root_user.groups << root
    @root_user.save
  end

  describe 'with no-group-user' do
    subject do
      Ixtlan::Users::Manager.new(@no_user, :application)
    end

    it 'should not allow any groups' do
      subject.allowed_group_ids(:group_ids => [1,2,3,5]).should == []
      subject.new_group_ids(@user, :group_ids => [1,2,3,5]).should == @user.groups.collect {|g| g.id}
    end
  end

  describe 'with user' do
    subject do
      Ixtlan::Users::Manager.new(@current_user, :application)
    end

    it 'should allow some groups' do
      subject.allowed_group_ids(:group_ids => [1,2,3,5, @g1.id]).should == [@g1.id]
      subject.new_group_ids(@user, :group_ids => [1,2,3,5]).sort.should == @user.groups.collect {|g| g.id}.sort
      subject.new_group_ids(@user, :group_ids => []).should == @user.groups.collect {|g| g.id} - [@g1.id]
      subject.update(@user).should == true
    end

    it 'should allow any groups with applications' do
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a2.id]
                        }]}).should == true
      ApplicationsGroupsUser.all.size.should == 4
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 2
      
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a3.id]
                        }]}).should == true
      ApplicationsGroupsUser.all.size.should == 4
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 2
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a1.id]
                        }]}).should == true
      ApplicationsGroupsUser.all.size.should == 3
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 1
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a2.id, @a3.id]
                        }]}).should == true
      ApplicationsGroupsUser.all.size.should == 5
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 3
    end
  end

  describe 'with root-user' do
    subject do
      Ixtlan::Users::Manager.new(@root_user, :application)
    end

    it 'should allow any groups' do
      subject.allowed_group_ids(:group_ids => [1,2,3,5]).should == [1,2,3,5]
      subject.new_group_ids(@user, :group_ids => [5]).should == @user.groups.collect {|g| g.id} + [5]
    end

    it 'should allow any groups with applications' do
      subject.allowed_group_ids(:group_ids => [1,2,3,5]).should == [1,2,3,5]
      subject.new_group_ids(@user, :group_ids => [5]).should == @user.groups.collect {|g| g.id} + [5]
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a1.id]
                        }]}).should == true
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 1
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a2.id]
                        }]}).should == true
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 1
      subject.update(@user, 
                     { :groups => 
                       [{
                          :id => @g1.id,
                          :application_ids => [@a1.id, @a2.id]
                        }]}).should == true
      ApplicationsGroupsUser.all(:conditions => ["user_id = ? and group_id = ?", @user.id, @g1.id]).size.should == 2
    end
  end
end
