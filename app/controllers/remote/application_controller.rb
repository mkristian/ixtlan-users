require 'ixtlan/remote/constant_time_compare'
class Remote::ApplicationController < ApplicationController
   include Ixtlan::Remote::ConstantTimeCompare

  respond_to :json

  skip_before_filter :authorize
  before_filter :remote_permission

  protected

  def serializer(resource)
    if resource
      @_factory ||= Ixtlan::Babel::Factory.new
      @_factory.new(resource)
    end
  end

  def x_service_token
    request.headers['X-SERVICE-TOKEN']
  end

  def remote_permission
    @_remote_permission ||= 
      begin 
        perm = nil
        token = x_service_token
        raise "ip #{request.remote_ip} sent no token" unless token
        RemotePermission.all.each do |rp|
          perm = rp if constant_time_compare(rp.token, token)
        end
        raise "ip #{request.remote_ip} wrong authentication" unless perm 
        # if the perm.id == nil then do not check IP - needed when using clusters
        raise "ip #{request.remote_ip} not allowed" if (!perm.ip.blank? && request.remote_ip != perm.ip)
        perm
      end
  end
end
