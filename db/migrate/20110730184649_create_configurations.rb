class CreateConfigurations < ActiveRecord::Migration
  def self.up
    create_table :configurations do |t|
      t.integer :idle_session_timeout, :default => 15
      t.string :from_email
      t.belongs_to :application

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :configurations
  end
end
