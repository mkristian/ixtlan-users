package de.mkristian.ixtlan.users.client.restservices;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;


@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface ApplicationsRestService extends RestService {

  @GET @Path("/applications")
  void index(MethodCallback<List<Application>> callback);

  @GET @Path("/applications/{id}")
  void show(@PathParam("id") int id, MethodCallback<Application> callback);

  @POST @Path("/applications")
  void create(Application value, MethodCallback<Application> callback);

  @PUT @Path("/applications/{id}")
  void update(@PathParam("id") @Attribute("id") Application value, MethodCallback<Application> callback);

  @DELETE @Path("/applications/{id}")
  void destroy(@PathParam("id") @Attribute("id") Application value, MethodCallback<Void> callback);

  @POST @Path("/applications/{id}/groups")
  void createGroup(@PathParam("id") @Attribute("getApplicationId()") Group value, MethodCallback<Group> callback);

  @PUT @Path("/groups/{id}")
  void updateGroup(@PathParam("id") @Attribute("getId()") Group value, MethodCallback<Group> callback);

  @DELETE @Path("/groups/{id}")
  void destroyGroup(@PathParam("id") @Attribute("getId()") Group value, MethodCallback<Void> callback);

}
