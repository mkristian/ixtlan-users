class Group < ActiveRecord::Base
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
end
