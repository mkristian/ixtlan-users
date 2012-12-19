package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.RemotePermission;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface RemotePermissionsRestService extends RestService {

  @GET @Path("/remote_permissions")
  void index(MethodCallback<List<RemotePermission>> callback);

  @GET @Path("/remote_permissions/{id}")
  void show(@PathParam("id") int id, MethodCallback<RemotePermission> callback);

  @POST @Path("/remote_permissions")
  void create(RemotePermission value, MethodCallback<RemotePermission> callback);

  @PUT @Path("/remote_permissions/{id}")
  void update(@PathParam("id") @Attribute("id") RemotePermission value, MethodCallback<RemotePermission> callback);

  @DELETE @Path("/remote_permissions/{id}")
  void destroy(@PathParam("id") @Attribute("id") RemotePermission value, MethodCallback<Void> callback);

}
