# dynamic configuration through a Configuration singleton model

# configuration model
# -------------------
CONFIGURATION = Configuration
Users::Application.config.configuration_model = CONFIGURATION
begin
  config_instance = CONFIGURATION.instance
rescue
  # allow rake tasks to work without configuration migrated
  return
end

# notification email on errors and dump directory for the system dump
# the error dumps will be cleanup after the days to keeps dump expired
# --------------------------------------------------------------------
# config_instance.register("error_dumper") do |config|
#   Rails.configuration.error_dumper.dump_dir = config.errors_dir
#   Rails.configuration.error_dumper.email_from = config.errors_from
#   Rails.configuration.error_dumper.email_to = config.errors_to
#   Rails.configuration.error_dumper.keep_dumps = config.errors_keep_dump # days
# end

# idle session timeout configuration (in minutes)
# -----------------------------------------------
config_instance.register("session_idle_timeout") do |config|
  Rails.configuration.idle_session_timeout = config.idle_session_timeout
end

# audit log manager
# -----------------

# config.audit_manager.model = MyAudit # default: Audit
# config.audit_manager.username_method = :username # default: :login

# config_instance.register("audit_manager") do |config|
#   Rails.configuration.audit_manager.keep_log = config.keep_log # days
# end

# --------------------
# static configuration
# --------------------

# error dumper
# ------------
# notification email on errors and dump directory for the system dump. per 
# default there is no email notification and if email_from or email_to is
# missing then there is no email too

# config.error_dumper.dump_dir = Rails.root + "/log/errors" # default: log/errors
# config.error_dumper.email_from = "no-reply@example.com"
# config.error_dumper.email_to = "developer1@example.com,developer2@example.com"
# config.error_dumper.keep_dumps = 30 # days
# config.skip_rescue_module = true # do not include the predefined Rescue 

# idle session timeout configuration
# ----------------------------------
# config.session_idle_timeout = 30 #minutes

# audit log manager
# -----------------
# config.audit_manager.model = MyAudit # default: Audit
# config.audit_manager.username_method = :username # default: :login
# config.audit_manager.keep_log = 30 # days
