class Group
  include ActiveModel::Serializers::JSON
  include ActiveModel::Serializers::Xml

  attr_accessor :name

  def attributes
    {'name' => name}
  end

  def initialize(attributes = {})
    @name = attributes['name']
  end
end
