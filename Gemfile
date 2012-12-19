source 'http://rubygems.org'
RAILS_VERSION = '~> 3.2.9'

gem 'activesupport',  RAILS_VERSION, :require => 'active_support'
gem 'actionpack',     RAILS_VERSION, :require => 'action_pack'
#gem 'actionmailer',   RAILS_VERSION, :require => 'action_mailer'
gem 'activerecord',   RAILS_VERSION, :require => 'active_record'
gem 'railties',       RAILS_VERSION, :require => 'rails'
gem 'tzinfo',         '~> 0.3.32' # TODO why explicit ????

# Bundle edge Rails instead:
# gem 'rails', :git => 'git://github.com/rails/rails.git'

platforms :ruby do
  gem 'sqlite3', :groups => [:development, :test]
end

platforms :jruby do

  gem 'activerecord-jdbc-adapter'

  # As rails --database switch does not support derby, hsqldb, h2 nor mssql
  # as valid values, if you are not using SQLite, comment out the SQLite gem
  # below and uncomment the gem declaration for the adapter you are using.
  # If you are using oracle, db2, sybase, informix or prefer to use the plain
  # JDBC adapter, comment out all the adapter gems below.

  # SQLite JDBC adapter
  gem 'jdbc-sqlite3', :require => false

  # Derby JDBC adapter
  #gem 'activerecord-jdbcderby-adapter'

  # HSQL JDBC adapter
  #gem 'activerecord-jdbchsqldb-adapter'

  # H2 JDBC adapter
  #gem 'activerecord-jdbch2-adapter'

  # SQL Server JDBC adapter
  #gem 'activerecord-jdbcmssql-adapter'

end

group :production do
  gem 'pg', :platforms => :ruby
end

#gem 'resty-generators', '~> 0.7.3'#, :path => '../../rails-resty-gwt/resty-generators'
gem 'ixtlan-generators', '~> 0.1.5'#, :path => '../../ixtlan/ixtlan-generators'

gem 'enforce-ssl'
gem "ixtlan-session-timeout", '~> 0.4.0'
gem "ixtlan-guard", '~> 0.9.0',:path => '../../ixtlan/ixtlan-guard'
gem "ixtlan-core", '~> 0.8.0',:path => '../../ixtlan/ixtlan-core'
gem "ixtlan-error-handler", '~> 0.2.1', :path => '../../ixtlan/ixtlan-error-handler'
gem "ixtlan-audit", '~> 0.2.1'#, :path => '../../ixtlan/ixtlan-audit'
gem 'slf4r', '~> 0.4.2'
gem 'bcrypt-ruby'

gem 'json', '1.6.1'

group :development, :test do
  gem 'rspec-rails', '~> 2.11.0'
  gem 'capybara'
  gem 'resty-generators', '~> 0.7.3', :path => '../../gwt/rails-resty-gwt/resty-generators'
end

gem 'ixtlan-remote', :path => '../../ixtlan/ixtlan-remote'
gem "ixtlan-babel", :path => '../../ixtlan/ixtlan-babel'
gem "ixtlan-optimistic", :path => '../../ixtlan/ixtlan-optimistic'
gem 'virtus', '~>0.5.2'
gem 'rest-client', '~> 1.6.7'
#gem 'backports', :platforms => :ruby_18


gem 'copyright-header', '~> 1.0.7', :group => :development
gem 'pony', '~> 1.4'
