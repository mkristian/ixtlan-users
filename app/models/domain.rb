class Domain < ActiveRecord::Base

  include ActiveModel::Dirty

  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-z0-9\.]+$/, :length => { :maximum => 32 }

  def self.all_changed_after(from)
    unless from.blank?
      Domain.all(:conditions => ["updated_at > ?", from])
    else
      Domain.all
    end
  end

end
