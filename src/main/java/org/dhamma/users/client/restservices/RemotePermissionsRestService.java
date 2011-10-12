package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.*;

import org.dhamma.users.client.models.*;


@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface RemotePermissionsRestService extends RestService {

  @GET @Path("/remote_permissions")
  @Options(dispatcher = DefaultDispatcherSingleton.class)
  void index(MethodCallback<List<RemotePermission>> callback);

//  @GET @Path("/remote_permissions")
//  void index(MethodCallback<List<RemotePermission>> callback, @QueryParam("limit") int limit, @QueryParam("offset") int offset);
//
  @GET @Path("/remote_permissions/{id}")
  void show(@PathParam("id") int id, MethodCallback<RemotePermission> callback);

  @POST @Path("/remote_permissions")
  void create(RemotePermission value, MethodCallback<RemotePermission> callback);

  @PUT @Path("/remote_permissions/{id}")
  void update(@PathParam("id") @Attribute("id") RemotePermission value, MethodCallback<RemotePermission> callback);

  @DELETE @Path("/remote_permissions/{id}")
  void destroy(@PathParam("id") @Attribute("id") RemotePermission value, MethodCallback<Void> callback);

}
