class AddUrlsToConfigurations < ActiveRecord::Migration
  def self.up
    add_column :configurations, :profile_url, :string
    add_column :configurations, :ats_url, :string
  end

  def self.down
    remove_column :configurations, :profile_url
    remove_column :configurations, :ats_url
  end
end
