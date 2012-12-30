class AddAllowedIpAndAuthenticationTokenToApplications < ActiveRecord::Migration
  def self.up
    add_column :applications, :allowed_ip, :string
    add_column :applications, :authentication_token, :string
  end

  def self.down
    remove_column :applications, :authentication_token
    remove_column :applications, :allowed_ip
  end
end
