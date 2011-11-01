class CreateErrors < ActiveRecord::Migration
  def self.up
    create_table :errors do |t|
      t.string :message
      t.text :request
      t.text :response
      t.text :session
      t.text :parameters
      t.string :clazz
      t.text :backtrace

      t.timestamps
    end
  end

  def self.down
    drop_table :errors
  end
end
