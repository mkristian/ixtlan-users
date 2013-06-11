class Domain < ActiveRecord::Base

  include ActiveModel::Dirty

  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /\A[A-Za-z0-9\.]+\z/, :length => { :maximum => 32 }

  def self.all_changed_after( from )
    unless from.blank?
      Domain.all( :conditions => [ "updated_at > ?", from ] )
    else
      Domain.all
    end
  end

  def self.all_changed_after_of_app( from, app )
    set = unless from.blank?
      DomainsGroupsUser.joins( :group, :domain ).where( 'application_id = ? and domains.updated_at > ?', 
                                                        app.id, 
                                                        from )
    else
      DomainsGroupsUser.uniq.joins( :group => :application ).where( 'application_id = ?', 
                                                                    app.id )
    end
    set.collect { |d| d.domain }.uniq
  end
end
