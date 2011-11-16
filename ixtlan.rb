# ixtlan client template for rails applications

gem 'ssl-enforce'
gem "ixtlan-core", '~> 0.7.0'
gem "ixtlan-guard", '~> 0.7.0'
gem "ixtlan-error-handler", '~> 0.2.0'
gem "ixtlan-audit", '~> 0.2.0'
gem "ixtlan-session-timeout", '~> 0.4.0'

apply 'http://mkristian.github.com/ixtlan-users/generate.rb'
