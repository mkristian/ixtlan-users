# the error dumps will be cleanup after the days to keeps dump expired

Users::Application.config.error_dumper.block = Proc.new do |dumper|
  config = Confguration.instance
  dumper.base_url = config.errors_base_url
  dumper.from_email = config.errors_from_email
  dumper.to_emails = config.errors_to_emails
  dumper.keep_dumps = config.errors_keep_dumps || 30
end
Users::Application.config.error_dumper.model = Error
