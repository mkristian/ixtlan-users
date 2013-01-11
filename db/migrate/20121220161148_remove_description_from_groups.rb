class RemoveDescriptionFromGroups < ActiveRecord::Migration
  def self.up
    remove_column :groups, :description
  end

  def self.down
    add_column :groups, :description, :text
  end
end
