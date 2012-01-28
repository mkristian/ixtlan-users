class CreateGroupsUsers < ActiveRecord::Migration
 def self.up
   create_table :groups_users, :id => false do |t|
      t.integer :group_id
      t.integer :user_id
   end
   add_index :groups_users, [:group_id, :user_id], :unique => true
  end
  def self.down
    drop_index :groups_users
    drop_table :groups_users
  end
end
