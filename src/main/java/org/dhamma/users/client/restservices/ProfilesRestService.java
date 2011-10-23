package org.dhamma.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import org.dhamma.users.client.models.*;

@Path("/profile")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ProfilesRestService extends RestService {

  @GET
  void show(MethodCallback<Profile> callback);

  @PUT
  void update(Profile value, MethodCallback<Profile> callback);

}
