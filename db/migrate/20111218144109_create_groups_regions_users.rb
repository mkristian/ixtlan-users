class CreateGroupsRegionsUsers < ActiveRecord::Migration
 def self.up
   create_table :groups_regions_users, :id => false do |t|
      t.integer :group_id
      t.integer :region_id
      t.integer :user_id
    end
  end
  def self.down
    drop_table :groups_regions_users
  end
end
