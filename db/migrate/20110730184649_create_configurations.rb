class CreateConfigurations < ActiveRecord::Migration
  def self.up
    create_table :configurations do |t|
      t.integer :idle_session_timeout, :default => 15
      t.string :password_from_email
      t.string :login_url

      t.timestamps
    end
  end

  def self.down
    drop_table :configurations
  end
end
