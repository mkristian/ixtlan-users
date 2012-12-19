class Application < ActiveRecord::Base
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-zA-Z0-9 \-]+$/, :length => { :maximum => 32 }
  #TODO is the url required ? and why not ?
  validates :url, :format => /^https?\:\/\/[a-z0-9\-\.]+(\.[a-z0-9]+)*(\:[0-9]+)?(\/\S*)?$/, :length => { :maximum => 64 }, :allow_nil => true

  def self.THIS
    find_by_id(1) || new(:name => 'users')
  end

  def self.ALL
    find_by_id(2) || new(:name => 'ALL')
  end

  def self.ATS
    @ats ||= 
      begin
        ats = Application.new
        ats.id = -1
        ats.name = "ATs"
        ats.url = Configuration.instance.ats_url || "ATs url not configured"
        ats
      end
  end

  def self.options
    {
      :except => [:created_at, :modified_by_id]
    }
  end
  
  def self.update_options
    {
      :only => [:id, :name, :url, :updated_at], :root => 'application'
    }
  end

  def self.reduced_options
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
    if current_user.root?
      self.all(*args)
    else
      a1 = current_user.applications
      a2 = current_user.allowed_applications
      # a1 | a2 ==  a1 union a2
      # TODO is not (a1 | a2) enough ?
      all = (a1 | a2) - (a2.member?(Application.ALL) ? [Application.ATS] : [Application.ATS, Application.THIS])
      # the above seems not to work !!
      all.delete_if { |a| a.id == Application.ATS.id }
      all
    end
  end

  def self.all_changed_after(from)
    if from.blank?
      Application.all(:conditions => ["url NOT NULL"])
    else
      Application.all(:conditions => ["url NOT NULL and updated_at > ?", from])
    end
  end

  unless respond_to? :old_as_json
    alias :old_as_json :as_json
    def as_json(options = nil)
      old_as_json(options || self.class.reduced_options)
    end
  end

  unless respond_to? :old_to_xml
    alias :old_to_xml :to_xml
    def to_xml(options = nil)
      #hacky di hack
      groups = (options[:include]||{}).delete(:groups)
      old_to_xml(options || self.class.reduced_options)
      options[:include][:groups]= groups if groups
    end
  end

  def to_s
    "Application(#{name})"
  end
end
