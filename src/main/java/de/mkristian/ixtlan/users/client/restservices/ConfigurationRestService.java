package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.Configuration;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


@Path("/configuration")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ConfigurationRestService extends RestService {

  @GET
  void show(MethodCallback<Configuration> callback);

  @PUT
  void update(Configuration value, MethodCallback<Configuration> callback);

}
