require 'ixtlan/babel/serializer'

class ConfigurationSerializer < Ixtlan::Babel::Serializer

  root 'configuration'

  add_context(:single,
              :except => [:id, :modified_by_id],
              :include => {
                :modified_by => {
                  :only => [:id, :login, :name]
                }
              }
             )
end
