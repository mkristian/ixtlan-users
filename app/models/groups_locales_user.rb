class GroupsLocalesUser < ActiveRecord::Base
  belongs_to :group
  belongs_to :locale
  belongs_to :user
end
