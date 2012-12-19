package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.Application;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


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

}
