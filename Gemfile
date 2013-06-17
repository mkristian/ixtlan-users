source 'http://rubygems.org'
RAILS_VERSION = '~> 3.2.11'

gem 'activesupport',  RAILS_VERSION, :require => 'active_support'
gem 'actionpack',     RAILS_VERSION, :require => 'action_pack'
gem 'activerecord',   RAILS_VERSION, :require => 'active_record'
gem 'railties',       RAILS_VERSION, :require => 'rails'
gem 'tzinfo',         '~> 0.3.32' # TODO why explicit ????

platforms :ruby do
  gem 'sqlite3', :groups => [:development, :test]
  gem 'pg', :groups => :production
end

platforms :jruby do
  gem 'activerecord-jdbc-adapter', '~> 1.2'
  gem 'jdbc-sqlite3', :require => false
end

gem "ixtlan-session-timeout", '~> 0.4.0'
gem "ixtlan-guard", '~> 0.9'#,:path => '../../ixtlan/ixtlan-guard'
# TODO use cache headers from ixtlan-core
#gem "ixtlan-core", '~> 0.8.0',:path => '../../ixtlan/ixtlan-core'
gem "ixtlan-error-handler", '~> 0.3'#, :path => '../../ixtlan/ixtlan-error-handler'
gem "ixtlan-audit", '~> 0.3'#, :path => '../../ixtlan/ixtlan-audit'
gem 'ixtlan-user-management', '~> 0.1'#, :path => '../../ixtlan/ixtlan-remote'
gem 'ixtlan-remote', '~> 0.1'#, :path => '../../ixtlan/ixtlan-remote'
gem "ixtlan-babel", '~> 0.2'#, :path => '../../ixtlan/ixtlan-babel'
gem "ixtlan-gettext", '~> 0.1', :require => false#, :path => '../../ixtlan/ixtlan-gettext'
gem "ixtlan-optimistic", '~> 0.2.1'#, :path => '../../ixtlan/ixtlan-optimistic'

gem 'enforce-ssl'
gem 'slf4r', '~> 0.4.2'
gem 'bcrypt-ruby'
gem 'pony', '~> 1.4'

group :development, :test do
  gem 'rspec-rails', '~> 2.12.0'
#  gem 'capybara'
  gem 'resty-generators', '~> 0.7.3', :require => false#, :path => '../../gwt/rails-resty-gwt/resty-generators'
end

gem 'copyright-header', '~> 1.0.7', :group => :development, :platform => :mri
