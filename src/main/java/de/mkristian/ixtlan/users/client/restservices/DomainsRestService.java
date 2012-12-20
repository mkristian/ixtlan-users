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
import de.mkristian.ixtlan.users.client.models.Domain;


@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface DomainsRestService extends RestService {

  @GET @Path("/domains")
  void index(MethodCallback<List<Domain>> callback);

  @GET @Path("/domains/{id}")
  void show(@PathParam("id") int id, MethodCallback<Domain> callback);

  @POST @Path("/domains")
  void create(Domain value, MethodCallback<Domain> callback);

  @PUT @Path("/domains/{id}")
  void update(@PathParam("id") @Attribute("id") Domain value, MethodCallback<Domain> callback);

  @DELETE @Path("/domains/{id}")
  void destroy(@PathParam("id") @Attribute("id") Domain value, MethodCallback<Void> callback);

}
