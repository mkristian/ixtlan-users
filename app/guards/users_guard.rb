class UsersGuard
  def initialize(guard)
    #guard.name = "users"
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
