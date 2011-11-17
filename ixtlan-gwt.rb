# ixtlan client template for rails applications with GWT frontend

#TODO something with the rails version: Rails::VERSION::STRING

gem 'resty-generators'

run 'bundle install'

generate 'resty:setup', ENV['GWT_MODULE'] || 'com.example', '--session', '--menu'

apply 'http://mkristian.github.com/ixtlan-users/generate.rb'
