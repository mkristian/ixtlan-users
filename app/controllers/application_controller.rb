class ApplicationController < ActionController::Base
  protect_from_forgery

  protected

  def current_user(user = nil)
    session['user'] = user if user
    session['user']
  end

  private

  after_filter :csrf

  def csrf
    response.header['X-CSRF-Token'] = form_authenticity_token if current_user
  end
end
