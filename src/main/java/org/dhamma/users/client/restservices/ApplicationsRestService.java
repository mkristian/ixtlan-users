package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.*;

import org.dhamma.users.client.models.*;


@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ApplicationsRestService extends RestService {

  @GET @Path("/applications")
  @Options(dispatcher = DefaultDispatcherSingleton.class)
  void index(MethodCallback<List<Application>> callback);

//  @GET @Path("/applications")
//  void index(MethodCallback<List<Application>> callback, @QueryParam("limit") int limit, @QueryParam("offset") int offset);
//
  @GET @Path("/applications/{id}")
  void show(@PathParam("id") int id, MethodCallback<Application> callback);

  @POST @Path("/applications")
  void create(Application value, MethodCallback<Application> callback);

  @PUT @Path("/applications/{id}")
  void update(@PathParam("id") @Attribute("id") Application value, MethodCallback<Application> callback);

  @DELETE @Path("/applications/{id}")
  void destroy(@PathParam("id") @Attribute("id") Application value, MethodCallback<Void> callback);

}
