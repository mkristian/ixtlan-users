class CreateGroupsLocalesUsers < ActiveRecord::Migration
 def self.up
   create_table :groups_locales_users, :id => false do |t|
      t.integer :group_id
      t.integer :locale_id
      t.integer :user_id
    end 
    add_index :groups_locales_users, [:group_id, :locale_id, :user_id], :unique => true
  end
  def self.down    
    drop_index :groups_locales_users
    drop_table :groups_locales_users
  end
end
