package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.Group;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface GroupsRestService extends RestService {

  @GET @Path("/groups")
  void index(MethodCallback<List<Group>> callback);

  @GET @Path("/groups/{id}")
  void show(@PathParam("id") int id, MethodCallback<Group> callback);

  @POST @Path("/groups")
  void create(Group value, MethodCallback<Group> callback);

  @PUT @Path("/groups/{id}")
  void update(@PathParam("id") @Attribute("id") Group value, MethodCallback<Group> callback);

  @DELETE @Path("/groups/{id}")
  void destroy(@PathParam("id") @Attribute("id") Group value, MethodCallback<Void> callback);

}
