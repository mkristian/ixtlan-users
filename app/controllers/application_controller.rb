class ApplicationController < ActionController::Base
  protect_from_forgery

  rescue_from ::Exception, :with => :internal_server_error
  protected

  def current_user(user = nil)
    # TODO just put user_id and user.groups names into session 
    session['user'] = user if user
    session['user']
  end

  def x_service_token
    request.headers['X-SERVICE-TOKEN']
  end

  def remote_permission
    perm = RemotePermission.find_by_token(x_service_token)
    raise "ip #{request.remote_ip} wrong authentication" unless perm 
    # if the perm.id == nil then do not check IP - needed when using clusters
    raise "ip #{request.remote_ip} not allowed" if (!perm.ip.blank? && request.remote_ip != perm.ip)
  end

  def current_user_group_names
    if current_user
      app_ids = [Application.THIS.id, Application.ALL.id]
      group_names = current_user.groups.select do |g|
        app_ids.member?(g.application.id)
      end.collect do |g|
        g.name.to_s
      end
      group_names << 'profile' # to allow every user the profile page
      group_names
    else
      []
    end
  end

  private

  prepend_after_filter :csrf
  before_filter :csrf

  def csrf
    response.header['X-CSRF-Token'] = form_authenticity_token if current_user
puts response.header['X-CSRF-Token']
puts current_user

  end
end
