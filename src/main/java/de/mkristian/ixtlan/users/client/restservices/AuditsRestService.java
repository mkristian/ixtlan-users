package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.Audit;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface AuditsRestService extends RestService {

  @GET @Path("/audits")
  void index(MethodCallback<List<Audit>> callback);

  @GET @Path("/audits/{id}")
  void show(@PathParam("id") int id, MethodCallback<Audit> callback);

}
