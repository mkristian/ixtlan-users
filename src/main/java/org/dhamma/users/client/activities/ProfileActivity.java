package org.dhamma.users.client.activities;


import org.dhamma.users.client.models.Profile;
import org.dhamma.users.client.places.ProfilePlace;
import org.dhamma.users.client.restservices.ProfilesRestService;
import org.dhamma.users.client.views.ProfileView;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.RestfulActionEnum;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ProfileActivity extends AbstractActivity implements ProfileView.Presenter{

    private final ProfilePlace place;
    private final ProfilesRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ProfileView view;
    
    @Inject
    public ProfileActivity(@Assisted ProfilePlace place, Notice notice, ProfileView view,
            ProfilesRestService service, PlaceController placeController) {
	this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        display.setWidget(view.asWidget());
        view.setPresenter(this);
	load();
        view.reset(place.action);
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load() {
        view.setEnabled(false);
        service.show(new MethodCallback<Profile>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Profile: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Profile response) {
                view.reset(response);
                notice.setText(null);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Profile . . .");
        }
    }
    public void save() {
        Profile model = view.retrieveProfile();
        view.setEnabled(false);
        service.update(model, new MethodCallback<Profile>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Profile: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, Profile response) {
                goTo(new ProfilePlace(RestfulActionEnum.EDIT));
            }
        });
        notice.setText("saving Profile . . .");        
    }
}