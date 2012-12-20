class RemoveUpdatedAtFromErrorsAndAudits < ActiveRecord::Migration
  def self.up
    remove_column :errors, :updated_at
    remove_column :audits, :updated_at
  end

  def self.down
    add_column :audits, :updated_at, :date_time
    add_column :errors, :updated_at, :date_time
  end
end
