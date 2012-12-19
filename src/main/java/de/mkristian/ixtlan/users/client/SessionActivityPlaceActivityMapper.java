package de.mkristian.ixtlan.users.client;

import javax.inject.Inject;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.session.NeedsAuthorization;
import de.mkristian.gwt.rails.session.NilMethodCallback;
import de.mkristian.gwt.rails.session.NoAuthorization;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.places.LoginPlace;
import de.mkristian.ixtlan.users.client.restservices.SessionRestService;

public class SessionActivityPlaceActivityMapper extends ActivityPlaceActivityMapper {

    private final Guard guard;
    private final SessionRestService service;
    
    private final static NilMethodCallback NIL = new NilMethodCallback();
    
    @Inject
    public SessionActivityPlaceActivityMapper(ActivityFactory factory,
            Guard guard, Notice notice, SessionRestService service) {
        super(factory, notice);
        this.guard = guard;
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
            if(guard.hasSession()){
                if(!guard.isAllowed((RestfulPlace<?, ?>)place)){
                    notice.error("nothing to see");
                    return null;
                }
                //TODO move into a dispatch filter or callback filter
                guard.resetCountDown();
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
            if(guard.hasSession()){
                if(!guard.isAllowed((RestfulPlace<?, ?>)place)){
                    notice.error("nothing to see");
                    return null;
                }
                //TODO move into a dispatch filter or callback filter
                guard.resetCountDown();
                service.ping(NIL);
            }
            else {
                return LoginPlace.LOGIN.create(factory);
            }
        }
        return super.getActivity(place);
    }
}
