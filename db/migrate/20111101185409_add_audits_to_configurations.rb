class AddAuditsToConfigurations < ActiveRecord::Migration
  def self.up
    add_column :configurations, :audits_keep_logs, :integer, :default => 90
  end

  def self.down
    remove_column :configurations, :audits_keep_logs
  end
end
