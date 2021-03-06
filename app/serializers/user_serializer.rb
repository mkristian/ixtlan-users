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

  add_context(:for_app,
              :only => [:id, :login, :name, :email, :updated_at],
              :include=> {
                :groups => {
                  :only => [:id, :name],
                  :methods => [ :domains ],
                  :include => {
                    :domains => {
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
                  :methods => [:applications, :domains, :locales, :regions]
                }
              })

  add_context(:authenticate,
              :except => [:hashed, :hashed2, :created_at, :updated_at, :modified_by_id, :at_token, :email],
              :include => {
                :groups => {
                  :only => [:id, :name],
                  :methods => [:domains, :locales, :regions],
                  :include => {
                    :application => {
                      :only => [:id, :name]
                    }
                  }
                },
                :applications => {
                   :only => [:id, :name, :url]
                }
              })


  def setup_associations(options = {})
    methods = ((((options || {})[:include] || {})[:groups] || {})[:methods] || [])
    [:applications, :application_ids, :domains, :locales, :regions ].each do |m|
      if collection?
        each do |i|
          i.groups.each { |g| g.send( m, i ) } if methods.member? m
        end
      else
        to_model.groups.each { |g| g.send( m, self ) } if methods.member? m
      end
    end

  end
  private :setup_associations

  def setup_filter(options = nil)
    super
    setup_associations( filter.options )
  end
end
