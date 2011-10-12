# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
puts "you can use ENV['login'] and ENV['email'] to set a proper user for production"
u = User.new(:name => "Root", :login => ENV['login'] || "root", :email => ENV['email'] || "root@example.com")
u.id = 1
u.save
u.modified_by = u
u.save

g = Group.create(:name => "root", :modified_by => u)

u.groups << g
u.save

RemotePermission.create(:ip => 'localhost', :token => 'be happy', :modified_by => u)
