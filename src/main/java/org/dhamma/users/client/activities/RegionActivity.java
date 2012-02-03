package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.RegionEvent;
import org.dhamma.users.client.events.RegionEventHandler;
import org.dhamma.users.client.caches.RegionsCache;

import org.dhamma.users.client.models.Region;
import org.dhamma.users.client.places.RegionPlace;
import org.dhamma.users.client.restservices.RegionsRestService;
import org.dhamma.users.client.views.RegionView;

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
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class RegionActivity extends AbstractActivity implements RegionView.Presenter{

    private final RegionPlace place;
    private final RegionsRestService service;
    private final Notice notice;
    private final DisplayErrors errors;
    private final PlaceController placeController;
    private final RegionView view;
    private final RegionsCache cache;
    private EventBus eventBus;
    
    @Inject
    public RegionActivity(@Assisted RegionPlace place, final Notice notice, DisplayErrors errors,
            final RegionView view, RegionsRestService service, PlaceController placeController,
            RegionsCache cache) {
        this.place = place;
        this.notice = notice;
        this.errors = errors;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
        this.cache = cache;

        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;

        this.eventBus.addHandler(RegionEvent.TYPE, new RegionEventHandler() {

            public void onModelEvent(ModelEvent<Region> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of Region");
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
                view.edit(new Region());
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
        List<Region> models = cache.getOrLoadModels();
        if (models != null){
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void create() {
        Region model = view.flush();
        service.create(model, new MethodCallback<Region>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                switch (errors.showMessages(method, exception)) {
                case GENERAL:
                    notice.error("error creating Region", exception);
                }
            }

            public void onSuccess(Method method, Region response) {
                notice.finishLoading();
                eventBus.fireEvent(new RegionEvent(response, Action.CREATE));
                goTo(new RegionPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.loading();
    }

    public void load(int id) {
        Region model = cache.getModel(id);
        view.edit(model);
        if (model == null || model.getCreatedAt() == null) {
            service.show(id, new MethodCallback<Region>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading Region", exception);
                }

                public void onSuccess(Method method, Region response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new RegionEvent(response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void save() {
        Region model = view.flush();
        service.update(model, new MethodCallback<Region>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                switch (errors.showMessages(method, exception)) {
                case CONFLICT:
                    //TODO
                case GENERAL:
                    notice.error("error saving Region", exception);
                }
            }

            public void onSuccess(Method method, Region response) {
                notice.finishLoading();
                eventBus.fireEvent(new RegionEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final Region model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                switch (errors.showMessages(method, exception)) {
                case CONFLICT:
                    //TODO
                case GENERAL:
                    notice.error("error deleting Region", exception);
                }
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new RegionEvent(model, Action.DESTROY));
                RegionPlace next = new RegionPlace(RestfulActionEnum.INDEX);
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