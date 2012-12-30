class DropRemotePermissions < ActiveRecord::Migration
  def self.up
    drop_table :remote_permissions
  end

  def self.down
    create_table :remote_permissions do |t|
      t.string :ip
      t.string :token
      t.belongs_to :application

      t.timestamps

      t.references :modified_by
    end
  end
end
