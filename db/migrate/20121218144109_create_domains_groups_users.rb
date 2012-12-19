class CreateDomainsGroupsUsers < ActiveRecord::Migration
 def self.up
   create_table :domains_groups_users, :id => false do |t|
      t.integer :domain_id
      t.integer :group_id
      t.integer :user_id
    end 
    add_index :domains_groups_users, [:domain_id, :group_id, :user_id], :unique => true
  end
  def self.down    
    drop_index :domains_groups_users
    drop_table :domains_groups_users
  end
end
