class ConfigurationsGuard
  def initialize(guard)
    #guard.name = "configurations"
    guard.aliases = {:create=>:new, :update=>:edit}
    guard.action_map= {
       :index => [],
       :show => [],
       :new => [],
       :edit => [],
       :destroy => [],
    }
  end
end
