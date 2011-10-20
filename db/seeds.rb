# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
puts "you can use ENV['login'] and ENV['email'] to set a proper user for production"
u = User.new(:name => ENV['name'] || "Root", :login => ENV['login'] || "root", :email => ENV['email'] || "root@example.com")
u.id = 1
u.save
u.modified_by = u
u.save

this = Application.THIS
this.url = "http://localhost/users.html"
this.modified_by = u
this.save

all = Application.ALL
all.url = ''
all.modified_by = u
all.save

root = Group.ROOT
root.modified_by = u
root.save

u.groups << root

user_admin = Group.USER_ADMIN
user_admin.modified_by = u
user_admin.save

at = Group.AT
at.modified_by = u
at.save

unless ENV['email'] || ENV['login']
  # harded access credentials only for development
  dev = Application.create(:name => "development", 
                           :url => "http://localhost/Dev.html",
                           :modified_by => u)
  RemotePermission.create(:ip => '127.0.0.1', 
                          :token => 'be happy', 
                          :modified_by => u,
                          :application => dev)
  dev_root = Group.create(:name => "root", 
                          :modified_by => u, 
                          :application => dev)
  u.groups << dev_root
end

u.save


c = Configuration.instance
c.from_email = 'noreply@example.com'
c.modified_by = u
c.save
