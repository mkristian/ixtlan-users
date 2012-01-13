package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.users.client.models.At;
import org.dhamma.users.client.models.*;

@Options(dispatcher = DefaultDispatcherSingleton.class)
public interface AtsRestService extends RestService {

  @GET @Path("/ats")
  void index(MethodCallback<List<At>> callback);

  @GET @Path("/ats/{id}")
  void show(@PathParam("id") int id, MethodCallback<At> callback);

}
