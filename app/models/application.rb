class Application < ActiveRecord::Base

  belongs_to :modified_by, :class_name => "User"

  has_many :groups

  validates :modified_by_id, :presence => true
  validates :name, :presence => true, :format => /^[a-zA-Z0-9 \-]+$/, :length => { :maximum => 32 }
  # url is not required like in ALL application
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

  after_create do |app|
    unless [Application.THIS, Application.ALL].member? app
      app.group_create( app.modified_by, :name => 'root' )
#    app.group_create( app.modified_by, :name => 'user-admin' )
      app.group_create( app.modified_by, :name => 'translator' )
    end
  end

  def to_s
    "Application(#{name})"
  end

  def group_create( current_user, attributes )
    g = groups.new( attributes )
    g.modified_by = current_user
    g.save
    g
  end

  def group_update( current_user, updated_at, id, attributes )
    g = Group.optimistic_find( updated_at, id )
    if g.application.id != self.id
      raise "application(#{self.id}) and group.application(#{g.application.id}) mismatch"
    end
    g.update_attributes( attributes.merge( :modified_by => current_user ) )
    g
  end

  def group_delete( updated_at, id )
    g = Group.optimistic_find( updated_at, id )
    if g.application.id != self.id
      raise "application(#{self.id}) and group.application(#{g.application.id}) mismatch"
    end
    g.destroy
    g
  end
end
