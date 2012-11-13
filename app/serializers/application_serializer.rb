require 'ixtlan/babel/serializer'

class ApplicationSerializer < Ixtlan::Babel::Serializer

  model Application

  add_context(:update,
              :root => 'application',
              :only => [:id, :name, :url, :updated_at]
             )

  add_context(:collection,
              :root => 'application',
              :except => [:created_at, :modified_by_id]
             )

  add_context(:single,
              :root => 'application',
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )

  default_context_key :single
end
