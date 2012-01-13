class Application < ActiveRecord::Base
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-zA-Z0-9 \-]+$/, :length => { :maximum => 32 }
  validates :url, :presence => true, :format => /^http\:\/\/[a-z0-9\-\.]+\.[a-z]{2,3}(\/\S*)?$/, :length => { :maximum => 64 }

  def self.THIS
    find_by_id(1) || new(:name => 'THIS')
  end

  def self.ALL
    find_by_id(2) || new(:name => 'ALL')
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

  def self.filtered_all(current_user, *args)
    apps = current_user.root_group_applications
    if apps.member?(Application.ALL)
      self.all(*args)
    else
      a1 = current_user.applications
      a2 = current_user.root_group_applications
      # union
      (a1 | a2) - (a2.member?(Application.ALL) ? [] : [Application.THIS])
    end
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = nil)
      old_as_json(options || self.class.options)
    end
  end

  unless respond_to? :old_to_xml
    alias :old_to_xml :to_xml
    def to_xml(options = nil)
      #hacky di hack
      groups = (options[:include]||{}).delete(:groups)
      old_to_xml(options || self.class.options)
      options[:include][:groups]= groups if groups
    end
  end
end
