class Configuration < ActiveRecord::Base
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  def self.instance
    self.first || self.new
  end
end
