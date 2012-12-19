class AddHasDomainsAndHasLocalesToGroups < ActiveRecord::Migration
  def self.up
    add_column :groups, :has_domains, :boolean, :default => false
    add_column :groups, :has_locales, :boolean, :default => false
  end

  def self.down
    remove_column :groups, :has_domains
    remove_column :groups, :has_locales
  end
end
