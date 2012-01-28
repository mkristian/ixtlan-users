Users::Application.routes.draw do

  get '/ats/last_changes', :controller => :ats, :action => :last_changes

  resources :regions do
    collection do
      get :last_changes
    end
  end

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
    collection do
      get :last_changes
    end
    member do
      get :at
    end
  end

  resource :authentications do
    collection do
      post :reset_password
    end
  end

end
