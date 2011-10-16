rails generate scaffold user login:string email:string name:string --modified-by user --optimistic

rails generate scaffold profile name:string email:string password:password --optimistic --singleton

rails generate scaffold application name:string url:string --optimistic --modified-by

rails generate ixtlan:configuration_scaffold configuration from_email:string application:belongs_to --modified-by --optimistic

rails generate scaffold remote_permissions ip:string token:string application:belongs_to --modified-by --optimistic

rails generate scaffold group name:string description:text application:belongs_to --modified-by --optimistic

rails generate session_migration # comment out resty-generators and ixtlan-generators in Gemfile due to a bug
