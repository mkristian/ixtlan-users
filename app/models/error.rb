class Error < ActiveRecord::Base

  def self.options
    {
      :except => [:created_at, :updated_at]
    }
  end

  def self.single_options
    {
    }
  end
end
