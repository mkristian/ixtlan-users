require 'ixtlan/guard/abstract_session'

class Session < Ixtlan::Guard::AbstractSession
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml

  def self.authenticate(login, password)
    User.authenticate(login, password)
  end

end
