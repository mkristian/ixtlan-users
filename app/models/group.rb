class Group < ActiveRecord::Base
  belongs_to :application
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true

  attr_accessor :applications

  def self.ROOT
    find_by_id(1) || new(:name => 'root', :application => Application.THIS)
  end

  def self.USER_ADMIN
    find_by_id(2) || new(:name => 'user-admin', :application => Application.THIS)
  end

  def self.AT
    find_by_id(3) || new(:name => 'at', :application => Application.ALL)
  end

  def self.options
    {
      :except => [:created_at, :updated_at, :modified_by_id],
      :include => {
        :application => {
          :only => [:id, :name]
        }
      },
      :methods => [:application_ids]
    }
  end

  def self.single_options
    {
      :except => [:modified_by_id],
      :include => {
        :modified_by => {
          :only => [:id, :login, :name]
        },
        :application => {
          :except => [:created_at, :updated_at, :modified_by_id]
        }
      }
    }
  end

  def applications(user = nil)
    if self == Group.ROOT
      @applications = ApplicationsGroupsUser.where(:user_id => user.id, :group_id => id).collect { |agu| agu.application } if user
      @applications || []
    end
  end

  def application_ids(user = nil)
    if self == Group.ROOT
      @application_ids = ApplicationsGroupsUser.where(:user_id => user.id, :group_id => id).collect { |agu| agu.application_id } if user
      @application_ids || []
    end
  end

  private
  def self.permitted(group, current_application)
    if current_application == Application.ALL || current_application == group.application
      group
    else
      raise ActiveRecord::NotFound.new("application mismatch")
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
    apps = current_user.root_group_applications
    if apps.size == 0
      current_user.groups
    elsif apps.member?(Application.ALL)
      self.all
    else
      self.where(:application_id => apps)
    end
  end
end
