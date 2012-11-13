# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
puts "you can use ENV['login'] and ENV['email'] to set a proper user for production"
unless u = User.get(1)
  u = User.new(:name => "System", :login => "system", :email => "system@example.com")
  u.id = 1
  u.save
end

unless uu = User.get(2)
  uu = User.new(:name => ENV['name'] || "Root", :login => ENV['login'] || "root", :email => ENV['email'] || "root@example.com")
  uu.id = 2
  uu.save
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

unless ENV['email'] || ENV['login']
  # hard coded access credentials only for development
  unless dev = Application.find_by_name("development")
    dev = Application.create(:name => "development", 
                             :url => "http://localhost/Dev.html",
                             :modified_by => u)
  end
  unless RemotePermission.find_by_token( 'be happy')
    RemotePermission.create(:ip => '127.0.0.1', 
                            :token => 'be happy', 
                            :modified_by => u,
                            :application => dev)
  end
  unless dev_root = Group.find_by_name_and_application('root', dev)
    Group.create(:name => "root", 
                 :modified_by => u, 
                 :application => dev)
    uu.groups << dev_root
    uu.save
  end
end

if defined? ::Configuration
  c.from_email = 'noreply@example.com'
  c = ::Configuration.instance
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
