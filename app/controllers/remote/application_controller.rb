require 'ixtlan/remote/access_controller'
class Remote::ApplicationController < ApplicationController

  respond_to :json, :xml, :yaml

  skip_before_filter :authorize
  before_filter :remote_permission

  protected

  def serializer(resource)
    if resource
      @_factory ||= Ixtlan::Babel::Factory.new
      @_factory.new(resource)
    end
  end

  include Ixtlan::Remote::AccessController

  def permission_model
    RemotePermission
  end
end
