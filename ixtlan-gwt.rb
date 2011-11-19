# ixtlan client template for rails applications with GWT frontend

#TODO something with the rails version: Rails::VERSION::STRING
apply 'http://jruby.org/templates/default.rb'

gem 'resty-generators'

unless defined? JRUBY_VERSION
  gem 'ruby-maven', :group => :development
end

run 'bundle install'

generate 'resty:setup', ENV['GWT_MODULE'] || 'com.example', '--session', '--menu', '--remote-users'

apply 'http://mkristian.github.com/ixtlan-users/generate.rb'

run 'rmvn bundle install'
run 'rake db:migrate'

