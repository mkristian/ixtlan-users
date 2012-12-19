require 'ixtlan/babel/serializer'
class SessionSerializer < Ixtlan::Babel::Serializer

  root 'session'

  add_context(:single,
              :only => [:permissions, :idle_session_timeout],
              :include=> { 
                :user => {
                  :only => [:id, :login, :name],
                  :include => [:applications]
                },
                :permissions => { 
                  :include => [:actions]
                }
              }
           )
end
