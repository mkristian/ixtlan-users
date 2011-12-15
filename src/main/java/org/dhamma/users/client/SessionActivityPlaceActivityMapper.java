package org.dhamma.users.client;

import javax.inject.Inject;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.LoginPlace;
import org.dhamma.users.client.restservices.SessionRestService;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.gwt.rails.session.NeedsAuthorization;
import de.mkristian.gwt.rails.session.NoAuthorization;
import de.mkristian.gwt.rails.session.SessionManager;

public class SessionActivityPlaceActivityMapper extends ActivityPlaceActivityMapper {

    private final SessionManager<User> manager;
    private final SessionRestService service;
    private final static MethodCallback<Void> NIL = new MethodCallback<Void>() {
        
        public void onSuccess(Method method, Void response) {
        }
        
        public void onFailure(Method method, Throwable exception) {
        }
    };
    
    @Inject
    public SessionActivityPlaceActivityMapper(ActivityFactory factory, SessionManager<User> manager, 
            Notice notice, SessionRestService service) {
        super(factory, notice);
        this.manager = manager;
        this.service = service;
    }

    public Activity getActivity(Place place) {
        return pessimisticGetActivity(place);
    }

    /**
     * pessimistic in the sense that default is authorisation, only the places
     * which implements {@link NoAuthorization} will be omitted by the check.
     */
    protected Activity pessimisticGetActivity(Place place) {
        if (!(place instanceof NoAuthorization)) {
            if(manager.hasSession()){
                if(!manager.isAllowed((RestfulPlace<?, ?>)place)){
                    notice.error("nothing to see");
                    return null;
                }
                manager.resetCountDown();
                service.ping(NIL);
            }
            else {
                return LoginPlace.LOGIN.create(factory);
            }
        }
        return super.getActivity(place);
    }

    /**
     * optimistic in the sense that default is no authorisation, only the places
     * which implements {@link NeedsAuthorization} will be checked.
     */
    protected Activity optimisticGetActivity(Place place) {
        if (place instanceof NeedsAuthorization) {
            if(manager.hasSession()){
                if(!manager.isAllowed((RestfulPlace<?, ?>)place)){
                    notice.error("nothing to see");
                    return null;
                }
                manager.resetCountDown();
            }
            else {
                return LoginPlace.LOGIN.create(factory);
            }
        }
        return super.getActivity(place);
    }
}