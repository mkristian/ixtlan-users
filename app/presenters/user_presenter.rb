class UserPresenter < JsonXmlPresenter

  set_default_view(:except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
                   :methods => [ :applications ])

  add_view(:collection, 
           :except => [:hashed, :hashed2, :created_at, :modified_by_id],
           :methods => [:group_ids, :application_ids])

  add_view(:remote_update, 
           :only => [:id, :login, :name, :updated_at])

  add_view(:at_remote_update, 
           :only => [:id, :name, :at_token, :updated_at], 
           :root => 'at')

  add_view(:single,
           :except => [:hashed, :hashed2, :modified_by_id], 
           :include => { 
             :modified_by => {
               :only => [:id, :login, :name],
             },
             :groups => {
               :only => [:id, :name, :application_id],
               :include => {
                 :application => {
                   :only => [:id, :name]
                 }
               },
               :methods => [:applications, :regions]
             }
           })

  add_view(:authenticate,
           :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id, :at_token, :email],
           :include => {
             :groups => {
               :only => [:id, :name],
               :methods => [:regions]
             }
           },
           :methods => [ :applications ])

  def setup_associations(options = {})
    methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
    
    to_model.groups.each { |g| g.applications(self) } if methods.member? :applications
    to_model.groups.each { |g| g.application_ids(self) } if methods.member? :application_ids
    to_model.groups.each { |g| g.regions(self) } if methods.member? :regions
  end

  def as_json(options = nil)
    opts = _options(options)
    setup_associations(opts)
    super(opts)
  end

  def to_json(options = nil)
    opts = _options(options)
    setup_associations(opts)
    super(opts)
  end

  def to_xml(options = nil)
    opts = _options(options)
    setup_associations(opts)
    super(opts)
  end

end
