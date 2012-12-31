class Region < ActiveRecord::Base

  include ActiveModel::Dirty

  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-zA-Z0-9 \-]+$/, :length => { :maximum => 32 }

  def self.all_changed_after(from)
    unless from.blank?
      Region.all(:conditions => ["updated_at > ?", from])
    else
      Region.all
    end
  end

  def self.options
    {
      :except => [:created_at, :modified_by_id]
    }
  end

  def self.single_options
    {
      :except => [:modified_by_id],
      :include => {
        :modified_by => {
          :only => [:id, :login, :name]
        }
      }
    }
  end

  def self.update_options
    {
      :only => [:id, :name, :updated_at]
    }
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = nil)
      old_as_json(options || self.class.options)
    end
  end
end
