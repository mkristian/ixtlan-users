class RemotePermission < ActiveRecord::Base
  belongs_to :application
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true

  def self.options
    {
      :except => [:created_at, :updated_at, :modified_by_id],
      :include => {
        :application => {
          :only => [:id, :name]
        }
      }
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

  def self.filtered_all(current_user, *args)
    apps = current_user.root_group_applications
    if apps.member?(Application.ALL)
      self.all(*args)
    else
      self.where(:application_id => apps)
    end
  end
end