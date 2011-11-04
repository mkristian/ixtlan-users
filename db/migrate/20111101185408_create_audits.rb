class CreateAudits < ActiveRecord::Migration
  def self.up
    create_table :audits do |t|
      t.string :login
      t.string :message

      t.timestamps
    end
  end

  def self.down
    drop_table :audits
  end
end
