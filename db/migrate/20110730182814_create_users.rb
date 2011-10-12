class CreateUsers < ActiveRecord::Migration
  def self.up
    create_table :users do |t|
      t.string :login
      t.string :email
      t.string :name
      t.string :hashed
      t.string :hashed2

      t.timestamps

      t.references :modified_by
    end
  end

  def self.down
    drop_table :users
  end
end
