class ApplicationController < ActionController::Base
 
  respond_to :json

  protected

  def serializer(resource)
    if resource
      @_factory ||= Ixtlan::Babel::Factory.new
      @_factory.new(resource)
    end
  end

  def login_and_password
    auth = params[:authentication] || params
    [ auth[:login] || auth[:email], auth[:password] ]
  end
  
end
  # old stuff
#   protect_from_forgery

#  # rescue_from ::Exception, :with => :internal_server_error

#   protected

#   def authorize_root_on_this
#     apps = current_user.allowed_applications
#     unless apps.member?(Application.ALL) || apps.member?(Application.THIS)
#       raise Ixtlan::Guard::PermissionDenied.new("only root of this application is allowed")
#     end
#   end

#   def authorize_application(id = params[:application_id])
# #   TODO  @application = Application.find(id)
#     @application = current_user.root? ? Application.ALL : current_user.allowed_applications.first
#     authorize_app(@application)
#     @application
#   end

#   def authorize_application_param(params)
#     app = params.delete(:application)
#     app_id = params.delete(:application_id)
#     app = Application.find(app_id) if app_id
#     if app
#       authorize_app(app)
#       params[:application] = app
#     end
#   end

#   def authorize_app(application)
#     authorize(application) do |group, app|
#       apps = group.applications(current_user)
#       apps.member?(app) || apps.member?(Application.ALL)
#     end
#   end

#   def current_user(user = nil)
#     # TODO just put user_id and user.groups names into session 
#     session['user'] = user if user
#     session['user']
#   end

#   def x_service_token
#     request.headers['X-SERVICE-TOKEN']
#   end

#   def remote_permission
#     perm = RemotePermission.find_by_token(x_service_token)
#     raise "ip #{request.remote_ip} wrong authentication" unless perm 
#     # if the perm.id == nil then do not check IP - needed when using clusters
#     raise "ip #{request.remote_ip} not allowed" if (!perm.ip.blank? && request.remote_ip != perm.ip)
#   end

#   def current_groups
#     if current_user
#       app_ids = [Application.THIS.id, Application.ALL.id]
#       groups = current_user.groups.select do |g|
#         app_ids.member?(g.application.id) || g == Group.ROOT
#       end
#       # to allow every user the profile page
#       groups << Group.new(:name => 'profile')
#       groups
#     else
#       []
#     end
#   end

# end
