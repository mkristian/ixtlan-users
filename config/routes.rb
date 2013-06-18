Users::Application.routes.draw do

  get '/ats/last_changes', :controller => 'remote/ats', :action => :last_changes
  get '/applications/last_changes', :controller => 'remote/applications', :action => :last_changes
  get '/regions/last_changes', :controller => 'remote/regions', :action => :last_changes
  get '/users/last_changes', :controller => 'remote/users', :action => :last_changes
  get '/users/last_changes_of_app', :controller => 'remote/users', :action => :last_changes_of_app
  put '/users/setup', :controller => 'remote/users', :action => :setup
  get '/domains/last_changes', :controller => 'remote/domains', :action => :last_changes
  get '/domains/last_changes_of_app', :controller => 'remote/domains', :action => :last_changes_of_app
  post '/authentications', :controller => 'remote/authentications', :action => :create
  post '/authentications/reset_password', :controller => 'remote/authentications', :action => :reset_password

  resources :domains

  resources :regions

  resources :audits

  resources :errors

  get '/groups', :controller => 'groups', :action => :index
  post '/applications/:id/groups', :controller => 'applications', :action => :group_create
  put '/groups/:id', :controller => 'applications', :action => :group_update
  delete'/groups/:id', :controller => 'applications', :action => :group_delete

  resources :applications

  resource :session do
    member do
      post :reset_password
      get :ping
    end
  end

  get '/profile', :controller => 'profile', :action => :show
  put '/profile', :controller => 'profile', :action => :update

  get '/configuration', :controller => 'configuration', :action => :show
  put '/configuration', :controller => 'configuration', :action => :update

  resources :users do
    member do
      get :at
    end
  end
end
