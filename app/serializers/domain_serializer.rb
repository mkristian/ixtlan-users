require 'ixtlan/babel/serializer'

class DomainSerializer < Ixtlan::Babel::Serializer

  model Domain

  add_context(:update,
              :root => 'domain',
              :only => [:id, :name, :updated_at]
             )

  add_context(:collection,
              :root => 'domain',
              :except => [:created_at, :modified_by_id]
             )

  add_context(:single,
              :root => 'domain',
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )

  default_context_key :collection
end
