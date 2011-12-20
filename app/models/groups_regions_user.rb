class GroupsRegionsUser < ActiveRecord::Base
  belongs_to :group
  belongs_to :region
  belongs_to :user
end
