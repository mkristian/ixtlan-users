run 'bundle install'

generate 'ixtlan:audit_scaffold'
generate 'ixtlan:error_dumps_scaffold'
generate 'ixtlan:configuration_scaffold'

rake 'db:migrate'

