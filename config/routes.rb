Users::Application.routes.draw do

  resource :session do
    member do
      post :reset_password
    end
  end

  resource :profile

  resource :configuration

  resources :users

  resources :authentications

end
