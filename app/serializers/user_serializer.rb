require 'ixtlan/babel/serializer'

class UserSerializer < Ixtlan::Babel::Serializer

  root 'user'

  add_context(:session,
              :only => [:id],
              :include=> { 
                :groups => {
                  :only => [:id],
                  :include => {
                    :domains => { 
                      :only => [:id, :name]
                    },
                    :locales => { 
                      :only => [:id, :code]
                    },
                    :regions => { 
                      :only => [:id, :name]
                    },
                    :application => {
                      :only => [:id, :name]
                    }                      
                  }
                }
              })

  add_context(:update,
              :only => [:id, :login, :name, :updated_at])

  add_context(:at_update, 
              :only => [:id, :name, :at_token, :updated_at], 
              :root => 'at')

  add_context(:collection, 
              :except => [:hashed, :hashed2, :created_at, :modified_by_id],
              :methods => [:group_ids, :application_ids])

  add_context(:profile,
              :root => 'profile',
              :except => [:hashed, :hashed2, :modified_by_id])

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
                  :methods => [:domains, :locales, :regions]
                },
                :applications => {
                   :only => [:id, :name, :url]
                }
              })

  
  def setup_associations(options = {})
    methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
  
    [:applications, :application_ids, :domains, :locales, :regions ].each do |m|

      to_model.groups.each { |g| g.send( m, self) } if methods.member? m
    end

  end
  private :setup_associations

  def to_json(options = nil)
    opts = filter.options.dup
    opts.merge!(options) if options
    setup_associations(opts) unless collection?
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
