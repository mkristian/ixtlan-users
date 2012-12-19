require 'ixtlan/babel/serializer'

class RegionSerializer < Ixtlan::Babel::Serializer

  root 'region'

  add_context(:update,
              :only => [:id, :name, :updated_at]
             )

  add_context(:collection,
              :except => [:created_at, :modified_by_id]
             )

  add_context(:single,
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )
end
