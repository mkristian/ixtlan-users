require 'spec_helper'

describe User do
  subject do
    u = User.first
    user = User.find_by_login('someone')
    user.delete if user
    user = User.new :login => 'someone', :name => 'me', :email => 'me@example.com'
    if u.nil?
      user.id = 1
      user.save
      u = user
    end
    user.modified_by = u
    user.save
    user
  end

  it 'should create a valid user' do
    subject.valid?.should == true
  end

  it 'should retrieve all as changed users without from date' do
    User.create(:login => 'someoneelse', :name => 'me', :email => 'someoneelse@example.com', :modified_by => subject) unless User.find_by_login('someoneelse')

    User.all.should == User.all_changed_after(nil)
  end

  it 'should retrieve all as changed users without from date' do
    last = 200.years.ago
    user = User.find_by_login('someoneelse') || User.create(:login => 'someoneelse', :name => 'me', :email => 'someoneelse@example.com', :modified_by => subject)
    User.all.each { |u| last = last < u.updated_at ? u.updated_at : last }

    User.all_changed_after(last).should == []
    user.reset_password_and_save
    User.all_changed_after(last).should == [user]
  end

  it 'should not authenticate' do
    u = User.authenticate(subject.login, "pwd")
    u.should == "user has no password: someone"
  end

  it 'should authenticate fails on empty login' do
    u = User.authenticate('', "pwd")
    u.should == "no login given"
  end

  it 'should authenticate fails on empty password' do
    u = User.authenticate(subject.login, "")
    u.should == "no password given with login: someone"
  end

  it 'should authenticate fails on with unknown login' do
    u = User.authenticate('bla', "pwd")
    u.should == "login not found: bla"
  end
  
  describe 'with password' do

    before do
      @pwd = subject.reset_password_and_save
    end

    it 'should authenticate' do
      u = User.authenticate(subject.login, @pwd)
      u.valid?.should == true
      u = User.authenticate(subject.email, @pwd)
      u.valid?.should == true
    end

    it 'should authenticate with old password after resetting it' do
      pwd = subject.reset_password_and_save
      u = User.authenticate(subject.login, @pwd)
      u.valid?.should == true
      u = User.authenticate(subject.login, pwd)
      u.should == "wrong password for login: someone"
    end

    it 'should authenticate with new password and removing old' do
      pwd = subject.reset_password_and_save
      u = User.authenticate(subject.login, pwd)
      u.valid?.should == true
      u = User.authenticate(subject.login, @pwd)
      u.should == "wrong password for login: someone"
    end

    it 'should fail authenticate with wrong password' do
      u = User.authenticate(subject.login, "wrong")
      u.should == "wrong password for login: someone"
      u = User.authenticate(subject.email, "Wrong")
      u.should == "wrong password for login: me@example.com"
    end

    it 'should send password email on password reset' do
      #index = ActionMailer::Base.deliveries.size
      pwd = User.reset_password(subject.login).password
      pwd.should_not be_nil
      #ActionMailer::Base.deliveries[index].body.raw_source.should =~ /#{pwd}/
      passwd = subject.reset_password_and_save
      passwd.should_not == pwd
      #ActionMailer::Base.deliveries[index + 1].body.raw_source.should =~ /#{passwd}/
    end

    describe 'and groups' do
      
      before do
        this = Application.THIS
        this.modified_by = User.first
        this.save

        all = Application.ALL
        all.modified_by = User.first
        all.save

        a1 = Application.find_by_name("spec-app1") || Application.create(:name => "spec-app1", :modified_by => User.first, :allowed_ip => "1.2.3.4", :authentication_token => '1234')
        @a2 = Application.find_by_name("spec-app2") || Application.create(:name => "spec-app2", :modified_by => User.first, :url => "http://example.com/app")

        @root = Group.ROOT
        @root.modified_by = User.first
        @root.save

        @g1 = Group.find_by_name("spec1") || Group.create(:name => "spec1", :modified_by => User.first, :application => a1)
        @g2 = Group.find_by_name("spec2") || Group.create(:name => "spec2", :modified_by => User.first, :application => @a2)
        subject.groups << @g1
        subject.groups << @g2
        subject.at_token = 'asd'
        subject.save
      end

      it 'should create a valid user with group_ids' do
        u = User.filtered_new({ :login => 'user1',
                                :name => 'User', 
                                :email => 'user1@example.com',
                                #:at_token => 'asd',
                                :group_ids => [@g1.id, @g2.id, @root.id]}, 
                              subject)
        u.modified_by = User.first
        u.deep_save.should == true
        u = u.reload
        u.groups.should == [@g1, @g2]
        u.deep_update_attributes({:group_ids => [@g1.id], :at_token => nil}, 
                                 subject).should == true
        u.reload.groups.should == [@g1]
      end

      it 'should setup a valid user with groups' do
        u = User.filtered_setup({ :login => 'user2',
                                  :name => 'User',
                                  :email => 'user2@example.com',
                                  :groups => [{:id => @g1.id},
                                              {:name => @g2.name}]},
                                @a2,
                                subject)
        u = u.reload
        u.groups.member?(@g1).should be_false
        u.groups.member?(@g2).should be_true
        u.groups.size.should == 1
        u.name.should == 'User'
        
        # just change name + email
        u = User.filtered_setup({ :login => 'user2',
                                  :name => 'user',
                                  :email => 'user2.user@example.com' },
                                @a2,
                                subject)
        u = u.reload
        u.name.should == 'user'
        u.email.should == 'user2.user@example.com' 
        
        # remove groups
        u = User.filtered_setup({ :login => 'user2',
                                  :groups => [] },
                                @a2,
                                subject)
        u = u.reload
        u.groups.member?(@g1).should be_false
        u.groups.member?(@g2).should be_false
        u.groups.size.should == 0
      end

      it 'should setup a valid user with groups and applications' do
        u = User.filtered_setup({ :login => 'user2',
                                  :name => 'User2',
                                  :email => 'user2@example.com',
                                  :groups => [{ :name => @g2.name, 
                                                :application_ids => [ @a2.id ] }]},
                                @a2,
                                User.first ) #root user
        u = u.reload
        u.groups.member?(@g1).should be_false
        u.groups.member?(@g2).should be_true
        u.groups.size.should == 1
        u.name.should == 'User2'
        u.email.should == 'user2@example.com'
        
        # application associations are restricted to root and app_admin users
        # so bypass that restriction
        apps = ApplicationsGroupsUser.where( :user_id => u.id ).collect { |a| a.application }
        apps.first.should == @a2
        apps.size.should == 1
        u = User.filtered_setup({ :login => 'user2',
                                  :name => 'User2',
                                  :email => 'user2@example.com',
                                  :groups => [{ :name => @g2.name, 
                                                :application_ids => [] }]},
                                @a2,
                                User.first ) #root user
        u = u.reload
        u.groups.size.should == 1
        # application associations are restricted to root and app_admin users
        # so bypass that restriction
        apps = ApplicationsGroupsUser.where( :user_id => u.id ).collect { |a| a.application }
        apps.size.should == 0
      end

      it 'should create a valid user with groups' do
        u = User.filtered_new({ :login => 'user3',
                                :name => 'User',
                                :email => 'user3@example.com',
                                :groups => [{:id => @g1.id},
                                            {:id => @g2.id}]}, 
                              subject)
        u.modified_by = User.first
        u.deep_save.should == true
        u = u.reload
        u.groups.member?(@g1).should be_true
        u.groups.member?(@g2).should be_true
        u.groups.size.should == 2
        u.deep_update_attributes({:groups => [{:id => @g1.id}, 
                                              {:id => @root.id}],
                                   :at_token => nil}, 
                                 subject).should == true
        u.reload.groups.should == [@g1]
        subject.groups << @root
        u.deep_update_attributes({:groups => [{:group => {:id => @g1.id}}, 
                                              {:group => {:id => @root.id}}]}, 
                                 subject).should == true
        u.reload.groups.member?(@g1).should be_true
        u.groups.member?(@root).should be_true
        u.groups.size.should == 2
      end

      it 'should have all groups without given IP' do
        u = User.authenticate(subject.login, @pwd)
        u.groups.should == [@g1, @g2]
      end

      it 'should have selected groups with given IP' do
        u = User.authenticate(subject.login, @pwd, '1234')
        u.groups.should == [@g1]
      end

      it 'should have no groups with unknown IP' do
        u = User.authenticate(subject.login, @pwd, '8888')
        u.groups.should == []
      end

      it 'should give a list of application url' do
        subject.application_urls.should == ["application spec-app1 has no configure url", @a2.url]
      end


      it 'should send new user email' do
        #index = ActionMailer::Base.deliveries.size
        u = User.new(:login => "new-spec", :email => 'new-spec@example.com', :name => "NM", :modified_by => User.first)
        u.groups << @g1 # use group which has different id from Group.AT !
        passwd = u.reset_password_and_save
        passwd.should_not be_nil
        #ActionMailer::Base.deliveries[index].body.raw_source.should =~ /#{@g1.application.url}/
        #ActionMailer::Base.deliveries[index + 1].body.raw_source.should =~ /#{passwd}/
      end
    end
  end
end
