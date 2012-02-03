class AddHasRegionsToGroups < ActiveRecord::Migration
  def self.up
    add_column :groups, :has_regions, :boolean, :default => false
  end

  def self.down
    remove_column :groups, :has_regions
  end
end
