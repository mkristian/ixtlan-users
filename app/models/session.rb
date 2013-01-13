require 'ixtlan/user_management/session_model'

class Session < Ixtlan::UserManagement::Session

  # needed for respond_with
  extend ActiveModel::Naming

  # needed for respond_with
  def errors
    []
  end

  def id 
    ''
  end
end
