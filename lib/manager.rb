require 'ixtlan/users/manager'

class Manager < Ixtlan::Users::Manager

  def initialize(current_user)
    super(current_user, :application, :region)
  end
end
