class Session
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml

  attr_accessor :permissions, :user

  def self.create(login, password)
    user = User.authenticate(login, password) if login
    case user
    when String
      user # error message
    else
      result = new
      result.user = user
      result
    end
  end

  def idle_session_timeout
    Rails.application.config.idle_session_timeout
  end

  def attributes
    {'idle_session_timeout' => idle_session_timeout, 'permissions' => permissions, 'user' => user}
  end

  def id
    ""
  end
end
