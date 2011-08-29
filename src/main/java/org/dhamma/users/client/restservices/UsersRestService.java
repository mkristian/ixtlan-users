package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.*;

import org.dhamma.users.client.models.*;


@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface UsersRestService extends RestService {

  @GET @Path("/users")
  @Options(dispatcher = DefaultDispatcherSingleton.class)
  void index(MethodCallback<List<User>> callback);

//  @GET @Path("/users")
//  void index(MethodCallback<List<User>> callback, @QueryParam("limit") int limit, @QueryParam("offset") int offset);
//
  @GET @Path("/users/{id}")
  void show(@PathParam("id") int id, MethodCallback<User> callback);

  @POST @Path("/users")
  void create(User value, MethodCallback<User> callback);

  @PUT @Path("/users/{id}")
  void update(@PathParam("id") @Attribute("id") User value, MethodCallback<User> callback);

  @DELETE @Path("/users/{id}")
  void destroy(@PathParam("id") @Attribute("id") User value, MethodCallback<Void> callback);

}
