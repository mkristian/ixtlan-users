class Application < ActiveRecord::Base
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true

  def self.THIS
    find_by_id(1) || new(:name => 'THIS')
  end

  def self.ALL
    find_by_id(2) || new(:name => 'ALL')
  end

  def self.options
    {
      :except => [:created_at, :updated_at, :modified_by_id]
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

  def self.filtered_all(current_user, *args)
    apps = current_user.root_group_applications
    if apps.member?(Application.ALL)
      self.all(*args)
    else
      a1 = current_user.applications
      a2 = current_user.root_group_applications
      # union
      a1 | a2
    end
  end
end
