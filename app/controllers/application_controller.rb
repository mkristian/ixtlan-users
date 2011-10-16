class ApplicationController < ActionController::Base
  protect_from_forgery

  protected

  def current_user(user = nil)
    # TODO just put user_id and user.groups names into session 
    session['user'] = user if user
    session['user']
  end

  def remote_permission
    perm = RemotePermission.find_by_token(request.headers['X-SERVICE-TOKEN'])
    raise "ip #{request.remote_ip} wrong authentication" unless perm 
    # if the perm.id == nil then do not check IP - needed when using clusters
    raise "ip #{request.remote_ip} not allowed" if (!perm.ip.blank? && request.remote_ip != perm.ip)
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
