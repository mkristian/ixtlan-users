Users::Application.config.audit_manager.block = Proc.new do |manager|
  manager.keep_log = Configuration.instance.audits_keep_logs
end
Users::Application.config.audit_manager.model = Audit
