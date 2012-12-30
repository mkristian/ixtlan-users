require 'ixtlan/babel/serializer'

class ApplicationSerializer < Ixtlan::Babel::Serializer

  root 'application'

  add_context(:update,
              :only => [:id, :name, :url, :updated_at]
             )

  add_context(:collection,
              :only => [:id, :name, :url, :updated_at]
             )

  add_context(:single,
              :except => [:modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                },
                :groups => {
                  :only => [:id, :name, :has_regions, :has_locales, :has_domains]
                }
              }
             )
end
