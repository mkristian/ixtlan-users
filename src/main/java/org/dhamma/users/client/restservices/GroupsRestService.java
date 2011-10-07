package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.*;

import org.dhamma.users.client.models.*;


@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface GroupsRestService extends RestService {

  @GET @Path("/groups")
  @Options(dispatcher = DefaultDispatcherSingleton.class)
  void index(MethodCallback<List<Group>> callback);

//  @GET @Path("/groups")
//  void index(MethodCallback<List<Group>> callback, @QueryParam("limit") int limit, @QueryParam("offset") int offset);
//
  @GET @Path("/groups/{id}")
  void show(@PathParam("id") int id, MethodCallback<Group> callback);

  @POST @Path("/groups")
  void create(Group value, MethodCallback<Group> callback);

  @PUT @Path("/groups/{id}")
  void update(@PathParam("id") @Attribute("id") Group value, MethodCallback<Group> callback);

  @DELETE @Path("/groups/{id}")
  void destroy(@PathParam("id") @Attribute("id") Group value, MethodCallback<Void> callback);

}
