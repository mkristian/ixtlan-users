require 'ixtlan/remote/sync'
class Updater < Ixtlan::Remote::Sync

  def initialize
    super Users::Application.config.rest
    register( Locale )
  end

end
