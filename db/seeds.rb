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

users = Application.create(:name => "users", 
                           :url => "http://localhost/Users.html",
                           :modified_by => u)
dev = Application.create(:name => "development", 
                         :url => "http://localhost/Dev.html",
                         :modified_by => u)

users_root = Group.create(:name => "root", 
                          :modified_by => u, 
                          :application => users)
dev_root = Group.create(:name => "root", 
                        :modified_by => u, 
                        :application => dev)

u.groups << users_root
u.groups << dev_root
u.save

RemotePermission.create(:ip => '127.0.0.1', 
                        :token => 'be happy', 
                        :modified_by => u,
                        :application => dev)

c = Configuration.instance
c.from_email = 'noreply@example.com'
c.application = users
c.modified_by = u
c.save
