package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.users.client.models.*;

@Path("/configuration")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ConfigurationsRestService extends RestService {

  @GET
  void show(MethodCallback<Configuration> callback);

  @PUT
  void update(Configuration value, MethodCallback<Configuration> callback);

}
