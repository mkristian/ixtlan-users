package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.ConfigurationEvent;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;
import org.dhamma.users.client.restservices.ApplicationsRestService;
import org.dhamma.users.client.restservices.ConfigurationsRestService;
import org.dhamma.users.client.views.ConfigurationView;

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
import de.mkristian.gwt.rails.events.ModelEvent.Action;

public class ConfigurationActivity extends AbstractActivity implements ConfigurationView.Presenter{

    private final ConfigurationPlace place;
    private final ConfigurationsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ConfigurationView view;
    private EventBus eventBus;
    
    @Inject
    public ConfigurationActivity(@Assisted ConfigurationPlace place, final Notice notice, final ConfigurationView view,
            ConfigurationsRestService service, PlaceController placeController, ApplicationsRestService applicationRestService) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
    
        view.resetApplications(null);
        applicationRestService.index(new MethodCallback<List<Application>>() {
            
            public void onSuccess(Method method, List<Application> response) {
                view.resetApplications(response);
            }
            
            public void onFailure(Method method, Throwable exception) {
                notice.setText("failed to load applications");
            }
        });
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;
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
                view.reset(place.action);
            }

            public void onSuccess(Method method, Configuration response) {
                eventBus.fireEvent(new ConfigurationEvent(response, Action.LOAD));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Configuration . . .");
        }
    }

    public void save() {
        Configuration model = view.flush();
        view.setEnabled(false);
        service.update(model.minimalClone(), new MethodCallback<Configuration>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error saving Configuration: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Configuration response) {
                eventBus.fireEvent(new ConfigurationEvent(response, Action.UPDATE));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        notice.setText("saving Configuration . . .");
    }
}