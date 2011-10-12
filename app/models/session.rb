class Session
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml

  attr_accessor :permissions, :user, :idle_session_timeout

  def self.create(login, password, &block)
    raise "block needed, using 'user' as argument and returning 'permissions'" unless block
    user = User.authenticate(login.to_s, password)
    
    result = new
    
    if user.valid?
      result.user = user
      result.permissions = block.call(user)
    else
      result.log = user.to_log # error message
    end
    result
  end

  def log=(msg)
    @log = msg
  end

  def to_log
    if @log
      @log
    else
      "Session(user-id: #{user.id}, idle-session-timeout: #{idle_session_timeout})"
    end
  end

  def valid?
    @log.nil?
  end

  def attributes
    {'idle_session_timeout' => idle_session_timeout, 'permissions' => permissions, 'user' => user}
  end
end
