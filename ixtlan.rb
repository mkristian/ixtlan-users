# ixtlan client template for rails applications

apply 'http://jruby.org/templates/default.rb'

gem "ixtlan-core", '~> 0.7.1'
gem "ixtlan-guard", '~> 0.7.0'
gem "ixtlan-session-timeout", '~> 0.4.0'

apply 'http://mkristian.github.com/ixtlan-users/generate.rb'

rake 'db:migrate'

