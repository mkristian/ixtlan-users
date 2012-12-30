# dynamic configuration through a Configuration singleton model

# configuration model
# -------------------
# Users::Application.config.configuration_model = :my_configuration # default :configuration

# notification email on errors and dump directory for the system dump
# the error dumps will be cleanup after the days to keeps dump expired
# --------------------------------------------------------------------
Users::Application.config.configuration_manager.register("error_dumper") do |config|
  Users::Application.config.error_dumper.base_url = config.errors_base_url
  Users::Application.config.error_dumper.from_email = config.errors_from_email
  Users::Application.config.error_dumper.to_emails = config.errors_to_emails
  Users::Application.config.error_dumper.keep_dumps = config.errors_keep_dumps || 30 # days
end

# idle session timeout configuration (in minutes)
# -----------------------------------------------
Users::Application.config.configuration_manager.register("session_idle_timeout") do |config|
  Users::Application.config.idle_session_timeout = config.idle_session_timeout || 15
end

# audit log manager
# -----------------

# Users::Application.config.audit_manager.model = :my_audit # default: :audit
# Users::Application.config.audit_manager.username_method = :username # default: :login

Users::Application.config.configuration_manager.register("audit_manager") do |config|
  Users::Application.config.audit_manager.keep_logs = config.audits_keep_logs || 90 # days
end
Users::Application.config.audit_manager.model= Audit
