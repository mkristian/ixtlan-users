package org.dhamma.users.client.activities;

import org.dhamma.users.client.events.ConfigurationEvent;

import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;
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

import de.mkristian.gwt.rails.DisplayErrors;
import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.events.ModelEvent.Action;

public class ConfigurationActivity extends AbstractActivity implements ConfigurationView.Presenter{

    private final ConfigurationsRestService service;
    private final Notice notice;
    private final DisplayErrors errors;
    private final PlaceController placeController;
    private final ConfigurationView view;
    private EventBus eventBus;
    
    @Inject
    public ConfigurationActivity(@Assisted ConfigurationPlace place, final Notice notice, DisplayErrors errors,
            final ConfigurationView view, ConfigurationsRestService service, PlaceController placeController) {
        this.notice = notice;
        this.errors = errors;
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
        service.show(new MethodCallback<Configuration>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error loading Configuration", exception);
            }

            public void onSuccess(Method method, Configuration response) {
                notice.finishLoading();
                eventBus.fireEvent(new ConfigurationEvent(response, Action.LOAD));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void save() {
        Configuration model = view.flush();
        service.update(model, new MethodCallback<Configuration>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                switch (errors.showMessages(method, exception)) {
                case CONFLICT:
                    //TODO
                case GENERAL:
                    notice.error("error saving Configuration", exception);
                }
            }

            public void onSuccess(Method method, Configuration response) {
                notice.finishLoading();
                eventBus.fireEvent(new ConfigurationEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }
}