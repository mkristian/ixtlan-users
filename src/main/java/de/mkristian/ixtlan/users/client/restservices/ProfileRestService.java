package de.mkristian.ixtlan.users.client.restservices;

import de.mkristian.gwt.rails.dispatchers.RestfulDispatcherSingleton;
import de.mkristian.ixtlan.users.client.models.*;

import javax.ws.rs.*;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;


@Path("/profile")
@Options(dispatcher = RestfulDispatcherSingleton.class)
public interface ProfileRestService extends RestService {

  @GET
  void show(MethodCallback<Profile> callback);

  @PUT
  void update(Profile value, MethodCallback<Profile> callback);

}
