class CreateApplicationsGroupsUsers < ActiveRecord::Migration
 def self.up
   create_table :applications_groups_users, :id => false do |t|
      t.integer :application_id
      t.integer :group_id
      t.integer :user_id
    end
  end
  def self.down
    drop_table :applications_groups_users
  end
end
