require 'spec_helper'

describe User do
  subject do
    u = User.first
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
    User.create(:login => 'someoneelse', :name => 'me', :email => 'me@example.com', :modified_by => subject) unless User.find_by_login('someoneelse')

    User.all.should == User.all_changed_after(nil)
  end

  it 'should retrieve all as changed users without from date' do
    last = 200.years.ago
    user = User.find_by_login('someoneelse') || User.create(:login => 'someoneelse', :name => 'me', :email => 'me@example.com', :modified_by => subject)

    User.all.each { |u| last = last < u.updated_at ? u.updated_at : last }

    User.all_changed_after(last).should == []
    user.reset_password_and_save
    User.all_changed_after(last).should == [user]
  end

  it 'should not authenticate' do
    u = User.authenticate(subject.login, "pwd")
    u.valid?.should == false
    u.to_log.should == "user has no password: someone"
  end

  it 'should authenticate fails on empty login' do
    u = User.authenticate('', "pwd")
    u.valid?.should == false
    u.to_log.should == "no login given"
  end

  it 'should authenticate fails on empty password' do
    u = User.authenticate(subject.login, "")
    u.valid?.should == false
    u.to_log.should == "no password given with login: someone"
  end

  it 'should authenticate fails on with unknown login' do
    u = User.authenticate('bla', "pwd")
    u.valid?.should == false
    u.to_log.should == "login not found: bla"
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
      u.valid?.should == false
    end

    it 'should authenticate with new password and removing old' do
      pwd = subject.reset_password_and_save
      u = User.authenticate(subject.login, pwd)
      u.valid?.should == true
      u = User.authenticate(subject.login, @pwd)
      u.valid?.should == false
    end

    it 'should fail authenticate with wrong password' do
      u = User.authenticate(subject.login, "wrong")
      u.valid?.should == false
      u.to_log.should == "wrong password for login: someone"
      u = User.authenticate(subject.email, "Wrong")
      u.valid?.should == false
      u.to_log.should == "wrong password for login: me@example.com"
    end

    it 'should send password email on password reset' do
      index = ActionMailer::Base.deliveries.size
      pwd = User.reset_password(subject.login).password
      pwd.should_not be_nil
      ActionMailer::Base.deliveries[index].body.raw_source.should =~ /#{pwd}/
      passwd = subject.reset_password_and_save
      passwd.should_not == pwd
      ActionMailer::Base.deliveries[index + 1].body.raw_source.should =~ /#{passwd}/
    end

    describe 'and groups' do
      
      before do
        this = Application.THIS
        this.modified_by = User.first
        this.save

        all = Application.ALL
        all.modified_by = User.first
        all.save

        a1 = Application.find_by_name("spec-app1") || Application.create(:name => "spec-app1", :modified_by => User.first)
        a2 = Application.find_by_name("spec-app2") || Application.create(:name => "spec-app2", :modified_by => User.first, :url => "http://example.com/app")
        perm = RemotePermission.find_by_ip("1.2.3.4") || RemotePermission.create(:ip => "1.2.3.4", :token => '1234', :application => a1, :modified_by => User.first)
        @g1 = Group.find_by_name("spec1") || Group.create(:name => "spec1", :modified_by => User.first, :application => a1)
        @g2 = Group.find_by_name("spec2") || Group.create(:name => "spec2", :modified_by => User.first, :application => a2)
        subject.groups << @g1
        subject.groups << @g2
        subject.save
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
        subject.application_urls.should == ["application spec-app1 has no configure url", "http://example.com/app"]
      end


      it 'should send new user email' do
        index = ActionMailer::Base.deliveries.size
        u = User.new(:login => "new-spec", :name => "NM", :modified_by => User.first)
        u.groups << @g2
        passwd = u.reset_password_and_save
        passwd.should_not be_nil
        ActionMailer::Base.deliveries[index].body.raw_source.should =~ /#{passwd}/
        ActionMailer::Base.deliveries[index].body.raw_source.should =~ /#{@g2.application.url}/
      end
    end
  end
end
