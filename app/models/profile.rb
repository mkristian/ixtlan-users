class Profile < ActiveRecord::Base
  def self.instance
    self.first || self.new
  end
end
