require 'resty/abstract_session'

class Session < Resty::AbstractSession
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml

  def self.authenticate(login, password)
    User.authenticate(login, password)
  end

end
