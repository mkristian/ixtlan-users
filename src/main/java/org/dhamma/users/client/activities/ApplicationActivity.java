package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.ApplicationEvent;
import org.dhamma.users.client.events.ApplicationEventHandler;
import org.dhamma.users.client.caches.ApplicationsCache;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.places.ApplicationPlace;
import org.dhamma.users.client.restservices.ApplicationsRestService;
import org.dhamma.users.client.views.ApplicationView;

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
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class ApplicationActivity extends AbstractActivity implements ApplicationView.Presenter{

    private final ApplicationPlace place;
    private final ApplicationsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ApplicationView view;
    private final ApplicationsCache cache;
    private EventBus eventBus;
    
    @Inject
    public ApplicationActivity(@Assisted ApplicationPlace place, final Notice notice, final ApplicationView view,
            ApplicationsRestService service, PlaceController placeController,
            ApplicationsCache cache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
        this.cache = cache;

        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;

        this.eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler() {

            public void onModelEvent(ModelEvent<Application> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of Application");
                }
            }
        });

        display.setWidget(view.asWidget());

        switch(RestfulActionEnum.valueOf(place.action)){
            case EDIT: 
            case SHOW:
                load(place.id);
                break;
            case NEW:
                view.edit(new Application());
                break;
            case INDEX:
            default:
                load();
                break;
        }
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load(){
        List<Application> models = cache.getOrLoadModels();
        if (models != null){
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void create() {
        Application model = view.flush();
        service.create(model, new MethodCallback<Application>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error creating Application", exception);
            }

            public void onSuccess(Method method, Application response) {
                notice.finishLoading();
                eventBus.fireEvent(new ApplicationEvent(response, Action.CREATE));
                goTo(new ApplicationPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.loading();
    }

    public void load(int id) {
        Application model = cache.getModel(id);
        view.edit(model);
        if (model == null || model.getCreatedAt() == null) {
            service.show(id, new MethodCallback<Application>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading Application", exception);
                }

                public void onSuccess(Method method, Application response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new ApplicationEvent(response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void save() {
        Application model = view.flush();
        service.update(model, new MethodCallback<Application>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error saving Application", exception);
            }

            public void onSuccess(Method method, Application response) {
                notice.finishLoading();
                eventBus.fireEvent(new ApplicationEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final Application model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error deleting Application", exception);
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new ApplicationEvent(model, Action.DESTROY));
                ApplicationPlace next = new ApplicationPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.removeFromList(model);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.loading();
    }
}