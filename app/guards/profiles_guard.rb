class ProfilesGuard
  def initialize(guard)
    #guard.name = "profiles"
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
