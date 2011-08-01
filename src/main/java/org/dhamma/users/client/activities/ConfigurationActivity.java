package org.dhamma.users.client.activities;


import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;
import org.dhamma.users.client.restservices.ConfigurationsRestService;
import org.dhamma.users.client.views.ConfigurationView;

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

public class ConfigurationActivity extends AbstractActivity implements ConfigurationView.Presenter{

    private final ConfigurationPlace place;
    private final ConfigurationsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ConfigurationView view;
    
    @Inject
    public ConfigurationActivity(@Assisted ConfigurationPlace place, Notice notice, ConfigurationView view,
            ConfigurationsRestService service, PlaceController placeController) {
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
        service.show(new MethodCallback<Configuration>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Configuration: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, Configuration response) {
                view.reset(response);
                notice.setText(null);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Configuration . . .");
        }
    }
    public void save() {
        Configuration model = view.retrieveConfiguration();
        view.setEnabled(false);
        service.update(model, new MethodCallback<Configuration>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Configuration: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, Configuration response) {
                goTo(new ConfigurationPlace(RestfulActionEnum.EDIT));
            }
        });
        notice.setText("saving Configuration . . .");        
    }
}