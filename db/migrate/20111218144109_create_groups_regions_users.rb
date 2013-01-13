class CreateGroupsRegionsUsers < ActiveRecord::Migration
 def self.up
   create_table :groups_regions_users, :id => false do |t|
      t.integer :group_id
      t.integer :region_id
      t.integer :user_id
    end 
    add_index :groups_regions_users, [:group_id, :region_id, :user_id], :unique => true, :name => :index_groups_regions_users
  end
  def self.down    
    drop_index :groups_regions_users
    drop_table :groups_regions_users
  end
end
