Users::Application.routes.draw do

  get '/ats/last_changes', :controller => 'remote/ats', :action => :last_changes
  get '/applications/last_changes', :controller => 'remote/applications', :action => :last_changes
  get '/regions/last_changes', :controller => 'remote/regions', :action => :last_changes
  get '/users/last_changes', :controller => 'remote/users', :action => :last_changes
  get '/domains/last_changes', :controller => 'remote/domains', :action => :last_changes
  post '/authentications', :controller => 'remote/authentications', :action => :create
  post '/authentications/reset_password', :controller => 'remote/authentications', :action => :reset_password

  resources :regions

  resources :audits

  resources :errors

  resources :applications

  resources :remote_permissions

  resources :groups

  resource :session do
    member do
      post :reset_password
      put :ping
    end
  end

  resource :profile

  resource :configuration

  resources :users do
    member do
      get :at
    end
  end
end
