class AddAtTokenToUsers < ActiveRecord::Migration
  def self.up
    add_column :users, :at_token, :string
  end

  def self.down
    remove_column :users, :at_token
  end
end
