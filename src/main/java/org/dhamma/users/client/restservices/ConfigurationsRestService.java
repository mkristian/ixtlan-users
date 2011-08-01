package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.RestfulDispatcherSingleton;
import java.util.List;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.*;
import org.fusesource.restygwt.client.dispatcher.DefaultDispatcher;

import org.dhamma.users.client.models.*;

@Path("/configuration")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ConfigurationsRestService extends RestService {

  @GET
  void show(MethodCallback<Configuration> callback);

  @PUT
  void update(Configuration value, MethodCallback<Configuration> callback);

}
