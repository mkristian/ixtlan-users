package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.users.client.models.Audit;

@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface AuditsRestService extends RestService {

  @GET @Path("/audits")
  void index(MethodCallback<List<Audit>> callback);

  @GET @Path("/audits/{id}")
  void show(@PathParam("id") int id, MethodCallback<Audit> callback);

}
