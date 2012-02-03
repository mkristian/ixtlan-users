class CreateRegions < ActiveRecord::Migration
  def self.up
    create_table :regions do |t|
      t.string :name

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :regions
  end
end
