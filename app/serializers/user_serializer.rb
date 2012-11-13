require 'ixtlan/babel/serializer'

class UserSerializer < Ixtlan::Babel::Serializer

  model User

  add_context(:default,
              :root => 'user',
              :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id],
              :methods => [ :applications ]
             )

  add_context(:update,
              :root => 'user',
              :only => [:id, :login, :name, :updated_at])

  add_context(:at_update, 
              :only => [:id, :name, :at_token, :updated_at], 
              :root => 'at')

  add_context(:collection, 
              :except => [:hashed, :hashed2, :created_at, :modified_by_id],
              :methods => [:group_ids, :application_ids])

  add_context(:single,
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
  
  add_context(:authenticate,
              :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id, :at_token, :email],
              :include => {
                :groups => {
                  :only => [:id, :name],
                  :methods => [:regions]
                },
                :applications => {
                   :except => [:created_at, :updated_at, :modified_by_id]
                }
              })

  default_context_key :default
  
  def setup_associations(options = {})
    methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
    
    to_model.groups.each { |g| g.applications(self) } if methods.member? :applications
    to_model.groups.each { |g| g.application_ids(self) } if methods.member? :application_ids
    to_model.groups.each { |g| g.regions(self) } if methods.member? :regions
    p to_model.applications
  end
  private :setup_associations

  def to_json(options = nil)
    opts = filter.options.dup
    opts.merge!(options) if options
    setup_associations(opts)
    super
  end

  def to_xml(options = nil)
    opts = filter.options.dup
    opts.merge!(options) if options
    setup_associations(opts)
    super(opts)
  end

  def to_yaml(options = nil)
    opts = filter.options.dup
    opts.merge!(options) if options
    setup_associations(opts)
    super(opts)
  end
end
