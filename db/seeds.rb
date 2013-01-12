# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
puts "you can use ENV['login'] and ENV['email'] to set a proper user for production"
unless u = User.find_by_id(1)
  u = User.new(:name => "System", :login => "system", :email => "system@example.com")
  u.id = 1
  u.modified_by_id = 1
  u.save
end

unless uu = User.find_by_id(2)
  uu = User.new(:name => ENV['name'] || "Root", :login => ENV['login'] || "root", :email => ENV['email'] || "root@example.com")
  uu.id = 2
  uu.modified_by = u
  uu.save
end

this = Application.THIS
this.url = "http://localhost:3000/Users.html"
this.modified_by = u
this.save

all = Application.ALL
all.modified_by = u
all.save

root = Group.ROOT
root.modified_by = u
root.application = this
root.save
unless uu.root?
  uu.groups << root
  # allow the root group for root user to act on all applications
  ApplicationsGroupsUser.create(:application => all, :group => root, :user => uu)
  uu.save
end

user_admin = Group.USER_ADMIN
user_admin.modified_by = u
user_admin.application = this
user_admin.save

app_admin = Group.APP_ADMIN
app_admin.modified_by = u
app_admin.application = this
app_admin.save

at = Group.AT
at.modified_by = u
at.application = all
at.save

atu = User.find_by_id(3)
if ENV['login'].nil? && atu.nil?
  atu = User.new(:name => "AT", :login => "at", :email => "at@example.com")
  atu.id = 3
  atu.modified_by = u
  atu.at_token = 'at'
  atu.groups << at
  atu.save
end

unless r = Region.find_by_name( 'Europe' )
  r = Region.new( :name => 'Europe' )
  r.modified_by = u
  r.save
end

unless d = Domain.find_by_name( 'test' )
  d = Domain.new( :name => 'test' )
  d.modified_by = u
  d.save
end

unless ENV['email'] || ENV['login']
  # hard coded access credentials only for development
  unless dev = Application.find_by_name("development")
    dev = Application.create(:name => "development", 
                             :url => "http://localhost/Dev.html",
                             :authentication_token => 'behappy',
                             :allowed_ip => '127.0.0.1',
                             :modified_by => u)
  end
  unless dev_root = Group.first( :conditions => { :name => 'root', :application_id => dev } )
    dev_root = Group.create( :name => "root", 
                             :modified_by => u, 
                             :application => dev )
    uu.groups << dev_root
    uu.save
  end
end

if defined? ::Configuration
  c = ::Configuration.instance
  if c.new_record?
    c.from_email = 'noreply@example.com'
    if defined? Ixtlan::Errors
      c.errors_keep_dumps = 30
    end
    if defined? Ixtlan::Audit
      c.audits_keep_logs = 90
    end
    if defined? Ixtlan::Sessions
      c.idle_session_timeout = 15
    end
    c.modified_by = u
    c.save
  end
end
