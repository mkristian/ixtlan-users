#
# ixtlan_translations - webapp where you can manage translations of applications
# Copyright (C) 2012 Christian Meier
#
# This file is part of ixtlan_translations.
#
# ixtlan_translations is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# ixtlan_translations is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with ixtlan_translations.  If not, see <http://www.gnu.org/licenses/>.
#
class LocalController < ApplicationController

  protect_from_forgery

  protected

  before_filter :cleanup

  def updated_at
    @_updated_at ||= (params[ params_key ] || {})[ :updated_at ]
  end

  def params_key
    params[:controller].singularize
  end

  def cleanup
    params_filter = ParamsFilter.new
    model_params =  params[ params_key ]
    params[ :filtered ][ params_key ] = model_params
    params[ params_key ] = params_filter.filter( model_params )
    @_updated_at ||= params_filter.updated_at
  end

  private

  after_filter :csrf

  def csrf
    response.header['X-CSRF-Token'] = form_authenticity_token
  end

  protected

  def current_user(user = nil)  
    if user
      session['user'] = serializer(user).use(:session).to_hash
      @_current_user = user
    else
      @_current_user ||= 
        begin
          data = session['user']
          if data
            data = data.dup
            u = User.find( data.delete( 'id' ) )
            u.groups = data['groups'].collect do |g|
              Group.find( g['id'] )
            end
            u
          end
        end
    end
  end
    
  def current_groups
    if current_user
      app_ids = [Application.THIS.id, Application.ALL.id]
      groups = current_user.groups.select do |g|
        app_ids.member?(g.application.id) || g == Group.ROOT
      end
      # to allow every user the profile page
      groups << Group.new(:name => 'profile')
      groups
    else
      []
    end
  end

  # TODO they should be guard filters
  protected

  def authorize_root_on_this
    apps = current_user.allowed_applications
    unless apps.member?(Application.ALL) || apps.member?(Application.THIS)
      raise Ixtlan::Guard::PermissionDenied.new("only root of this application is allowed")
    end
  end

  def authorize_application(id = params[:application_id])
#   TODO  @application = Application.find(id)
    @application = current_user.root? ? Application.ALL : current_user.allowed_applications.first
    authorize_app(@application)
    @application
  end

  def authorize_application_param(params)
    app = params.delete(:application)
    app_id = params.delete(:application_id)
    app = Application.find(app_id) if app_id
    if app
      authorize_app(app)
      params[:application] = app
    end
  end

  def authorize_app(application)
    authorize(application) do |group, app|
      apps = group.applications(current_user)
      apps.member?(app) || apps.member?(Application.ALL)
    end
  end
end
