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

  def self.filtered_all(current_user, *args)
    apps = current_user.root_group_applications
    if apps.member?(Application.ALL)
      self.all(*args)
    else
      self.where(:application => apps)
    end
  end
end
