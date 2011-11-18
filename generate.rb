gem 'enforce-ssl'
gem "ixtlan-core", '~> 0.7.2'
gem "ixtlan-error-handler", '~> 0.2.0'
gem "ixtlan-audit", '~> 0.2.0'

run 'bundle install'

generate 'ixtlan:audit_scaffold'
generate 'ixtlan:error_dumps_scaffold'
generate 'ixtlan:configuration_scaffold'

