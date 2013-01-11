class Group < ActiveRecord::Base
  belongs_to :application
  belongs_to :modified_by, :class_name => "User"
  validates :application_id, :presence => true
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-zA-Z0-9_\-]+$/, :length => { :maximum => 32 }

  attr_accessor :applications, :regions, :locales, :domains

  def self.ROOT
    find_by_id(1) || new(:id => 1, :name => 'root', :application => Application.THIS)
  end

  def self.USER_ADMIN
    find_by_id(2) || new(:name => 'user-admin', :application => Application.THIS)
  end

  def self.APP_ADMIN
    find_by_id(3) || new(:name => 'app-admin', :application => Application.THIS)
  end

  def self.AT
    find_by_id(4) || new(:name => 'at', :application => Application.ALL)
  end

  def load_associations( model, method, user )
    if user
      model.where(:user_id => user.id, :group_id => id).collect { |item| item.send method }
    else
      []
    end
  end
  private :load_associations

  def regions(user = nil)
    if has_regions
      @regions = load_associations( GroupsRegionsUser, :region, user )
    end
  end

  def locales(user = nil)
    if has_locales
      @locales = load_associations( GroupsLocalesUser, :locale, user )
    end
  end

  def domains(user = nil)
    if has_domains
      @domains = load_associations( DomainsGroupsUser, :domain, user )
    end
  end

  def applications(user = nil)
    # TODO root needs to see ALL applications !!
    if self == Group.ROOT || self == Group.APP_ADMIN
      @applications = (ApplicationsGroupsUser.where(:user_id => user.id, :group_id => id).collect { |agu| agu.application } || []) if user
      @applications || []
    else
      []
    end
  end

  def application_ids(user = nil)
    # TODO root needs to see ALL applications !!
    if self == Group.ROOT || self == Group.APP_ADMIN
      @application_ids = ApplicationsGroupsUser.where(:user_id => user.id, :group_id => id).collect { |agu| agu.application_id } if user
      @application_ids || []
    end
  end

  def region_ids(user = nil)
    @region_ids = GroupsUsersRegion.where(:user_id => user.id, :group_id => id).collect { |item| item.region_id } if user
    @region_ids || []
  end

  private
  def self.permitted(group, current_application)
    if current_application == Application.ALL || current_application == group.application
      group
    else
      raise ActiveRecord::RecordNotFound.new("application mismatch")
    end
  end

  public
  def self.filtered_find(id, current_application)
    permitted(find(id), current_application)
  end

  def self.filtered_optimistic_find(updated_at, id, current_application)
    permitted(optimistic_find(updated_at, id), current_application)
  end

  def self.filtered_all(current_user)
    apps = current_user.allowed_applications
    if current_user.root?
      self.all
    elsif apps.size == 0
      current_user.groups
    else
      self.where(:application_id => apps)
    end
  end
end
