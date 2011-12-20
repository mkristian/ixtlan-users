package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.users.client.models.Region;
import org.dhamma.users.client.models.*;

@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface RegionsRestService extends RestService {

  @GET @Path("/regions")
  void index(MethodCallback<List<Region>> callback);

  @GET @Path("/regions/{id}")
  void show(@PathParam("id") int id, MethodCallback<Region> callback);

  @POST @Path("/regions")
  void create(Region value, MethodCallback<Region> callback);

  @PUT @Path("/regions/{id}")
  void update(@PathParam("id") @Attribute("id") Region value, MethodCallback<Region> callback);

  @DELETE @Path("/regions/{id}")
  void destroy(@PathParam("id") @Attribute("id") Region value, MethodCallback<Void> callback);

}
