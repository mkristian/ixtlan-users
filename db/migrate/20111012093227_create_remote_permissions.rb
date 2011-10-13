class CreateRemotePermissions < ActiveRecord::Migration
  def self.up
    create_table :remote_permissions do |t|
      t.string :ip
      t.string :token
      t.belongs_to :application

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :remote_permissions
  end
end
