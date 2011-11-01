Users::Application.routes.draw do

  resources :errors

  resources :users

  resources :applications

  resources :remote_permissions

  resources :roles

  resources :groups

  resource :session do
    member do
      post :reset_password
      get :current_groups
      put :ping
    end
  end

  resource :profile

  resource :configuration

  resources :users do
    collection do
      get :last_changes
    end
  end

  resource :authentications do
    collection do
      post :reset_password
    end
  end

end
