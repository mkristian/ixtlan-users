package org.dhamma.users.client.restservices;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.dhamma.users.client.models.Profile;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;

@Path("/profile")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ProfilesRestService extends RestService {

  @GET
  void show(MethodCallback<Profile> callback);

  @PUT
  void update(Profile value, MethodCallback<Profile> callback);

}
