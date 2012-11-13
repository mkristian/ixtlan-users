require 'ixtlan/babel/serializer'

class RegionSerializer < Ixtlan::Babel::Serializer

  model Region

  add_context(:update,
              :root => 'region',
              :only => [:id, :name, :updated_at]
             )

  add_context(:collection,
              :root => 'region',
              :except => [:created_at, :modified_by_id]
             )

  add_context(:single,
              :root => 'region',
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )

  default_context_key :collection
end
