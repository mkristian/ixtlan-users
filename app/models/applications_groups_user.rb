class ApplicationsGroupsUser < ActiveRecord::Base
  belongs_to :application
  belongs_to :group
  belongs_to :user
end
