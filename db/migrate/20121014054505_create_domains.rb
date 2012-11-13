class CreateDomains < ActiveRecord::Migration
  def self.up
    create_table :domains do |t|
      t.string :name

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :domains
  end
end
