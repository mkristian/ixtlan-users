class AddErrorsToConfigurations < ActiveRecord::Migration
  def self.up
    add_column :configurations, :errors_keep_dumps, :integer, :default => 30
    add_column :configurations, :errors_base_url, :string
    add_column :configurations, :errors_from_email, :string
    add_column :configurations, :errors_to_emails, :string
  end

  def self.down
    remove_column :configurations, :errors_keep_dumps
    remove_column :configurations, :errors_base_url
    remove_column :configurations, :errors_from_email
    remove_column :configurations, :errors_to_emails
  end
end
