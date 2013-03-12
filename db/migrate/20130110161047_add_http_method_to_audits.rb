class AddHttpMethodToAudits < ActiveRecord::Migration
  def self.up
    add_column :audits, :http_method, :string
  end

  def self.down
    remove_column :audits, :http_method
  end
end
