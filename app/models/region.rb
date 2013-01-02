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
end
