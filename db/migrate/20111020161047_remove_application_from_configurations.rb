class RemoveApplicationFromConfigurations < ActiveRecord::Migration
  def self.up
    remove_column :configurations, :application_id
  end

  def self.down
    add_column :configurations, :application_id, :integer
  end
end
