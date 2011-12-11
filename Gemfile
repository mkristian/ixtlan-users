source 'http://rubygems.org'

gem 'rails', '3.0.9'

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


# Use unicorn as the web server
# gem 'unicorn'

# Deploy with Capistrano
# gem 'capistrano'

# To use debugger (ruby-debug for Ruby 1.8.7+, ruby-debug19 for Ruby 1.9.2+)
# gem 'ruby-debug'
# gem 'ruby-debug19', :require => 'ruby-debug'

# Bundle the extra gems:
# gem 'bj'
# gem 'nokogiri'
# gem 'sqlite3-ruby', :require => 'sqlite3'
# gem 'aws-s3', :require => 'aws/s3'

# Bundle gems for the local environment. Make sure to
# put test-only gems in this group so their generators
# and rake tasks are available in development mode:
# group :development, :test do
#   gem 'webrat'
# end

group :production do
  gem 'pg', :platforms => :ruby
end

gem 'resty-generators', '~> 0.7.0'#, :path => '../../rails-resty-gwt/resty-generators'
gem 'ixtlan-generators', '~> 0.1.3'#, :path => '../../ixtlan/ixtlan-generators'

gem 'enforce-ssl'
gem "ixtlan-session-timeout", '~> 0.4.0'
gem "ixtlan-guard", '~> 0.7.0'
gem "ixtlan-core", '~> 0.7.0'#,:path => '../../ixtlan/ixtlan-core'
gem "ixtlan-error-handler", '~> 0.2.0'#, :path => '../../ixtlan/ixtlan-error-handler'
gem "ixtlan-audit", '~> 0.2.0'#, :path => '../../ixtlan/ixtlan-audit'
#gem 'slf4r', '~> 0.4.2'
gem 'bcrypt-ruby'

gem 'rspec-rails', '~> 2.7.0', :group => :development
gem 'rspec', '~> 2.7.0', :group => :development
gem 'json', '1.6.1'
