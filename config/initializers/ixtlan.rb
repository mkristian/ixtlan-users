# dynamic configuration through a Configuration singleton model

# configuration model
# -------------------
# Users::Application.config.configuration_model = :my_configuration # default :configuration

# notification email on errors and dump directory for the system dump
# the error dumps will be cleanup after the days to keeps dump expired
# --------------------------------------------------------------------
Users::Application.config.configuration_manager.register("error_dumper") do |config|
  Users::Application.config.error_dumper.dump_dir = config.errors_dir
  Users::Application.config.error_dumper.email_from = config.errors_from
  Users::Application.config.error_dumper.email_to = config.errors_to
  Users::Application.config.error_dumper.keep_dumps = config.errors_keep_dump # days
end

# idle session timeout configuration (in minutes)
# -----------------------------------------------
Users::Application.config.configuration_manager.register("session_idle_timeout") do |config|
  Users::Application.config.session_idle_timeout = config.session_idle_timeout
end

# audit log manager
# -----------------

# Users::Application.config.audit_manager.model = :my_audit # default: :audit
# Users::Application.config.audit_manager.username_method = :username # default: :login

Users::Application.config.configuration_manager.register("audit_manager") do |config|
  Users::Application.config.audit_manager.keep_logs = config.keep_logs # days
end
