package org.dhamma.users.client.activities;

import org.dhamma.users.client.events.ProfileEvent;
import org.dhamma.users.client.events.ProfileEventHandler;
import org.dhamma.users.client.models.Profile;
import org.dhamma.users.client.places.ProfilePlace;
import org.dhamma.users.client.restservices.ProfilesRestService;
import org.dhamma.users.client.views.ProfileView;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;

public class ProfileActivity extends AbstractActivity implements ProfileView.Presenter{

    private final ProfilePlace place;
    private final ProfilesRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ProfileView view;
    private EventBus eventBus;
    
    @Inject
    public ProfileActivity(@Assisted ProfilePlace place, final Notice notice, final ProfileView view,
            ProfilesRestService service, PlaceController placeController) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;

        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;

        display.setWidget(view.asWidget());

        load();
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load() {
        service.show(new MethodCallback<Profile>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error loading Profile", exception);
            }

            public void onSuccess(Method method, Profile response) {
                notice.finishLoading();
                eventBus.fireEvent(new ProfileEvent(response, Action.LOAD));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void save() {
        Profile model = view.flush();
        service.update(model, new MethodCallback<Profile>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error saving Profile", exception);
            }

            public void onSuccess(Method method, Profile response) {
                notice.finishLoading();
                eventBus.fireEvent(new ProfileEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }
}