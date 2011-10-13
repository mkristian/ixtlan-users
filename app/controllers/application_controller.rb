class ApplicationController < ActionController::Base
  protect_from_forgery

  protected

  def current_user(user = nil)
    session['user'] = user if user
    session['user']
  end

  def ip_restriction
    perm = RemotePermission.find_by_ip(request.remote_ip)
    raise "ip #{request.remote_ip} not allowed" unless perm 
    raise "ip #{request.remote_ip} wrong authentication" unless (request.headers['X-SERVICE-TOKEN'] == perm.token)
  end

  def groups_for_current_users
    app_id = Configuration.instance.application.nil? ? 0 :Configuration.instance.application.id
    current_user.groups.select do |g|
      g.application.id != app_id
    end.collect do |g|
      g.name
    end
  end

  private

  after_filter :csrf

  def csrf
    response.header['X-CSRF-Token'] = form_authenticity_token if current_user
  end
end
