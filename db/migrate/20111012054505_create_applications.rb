class CreateApplications < ActiveRecord::Migration
  def self.up
    create_table :applications do |t|
      t.string :name
      t.string :url

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :applications
  end
end
