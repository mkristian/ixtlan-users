package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.ApplicationEvent;
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
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class ApplicationActivity extends AbstractActivity implements ApplicationView.Presenter{

    private final ApplicationPlace place;
    private final ApplicationsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final ApplicationView view;
    private EventBus eventBus;
    
    @Inject
    public ApplicationActivity(@Assisted ApplicationPlace place, final Notice notice, final ApplicationView view,
            ApplicationsRestService service, PlaceController placeController) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;
        display.setWidget(view.asWidget());
        view.setPresenter(this);
        switch(RestfulActionEnum.valueOf(place.action.name())){
            case EDIT: 
            case SHOW:
                load(place.model == null ? place.id : place.model.getId());
                break;
            case NEW:
                notice.setText(null);
                view.edit(new Application());
                break;
            case INDEX:
            default:
                load();
                break;
        }
        view.reset(place.action);
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load(){  
        view.setEnabled(false);
        service.index(new MethodCallback<List<Application>>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading list of Application: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, List<Application> response) {
                eventBus.fireEvent(new ApplicationEvent(response, Action.LOAD));
                notice.setText(null);
                view.reset(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading list of Application . . .");
        }
    }

    public void create() {
        Application model = view.flush();
        view.setEnabled(false);
        service.create(model, new MethodCallback<Application>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error creating Application: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Application response) {
                eventBus.fireEvent(new ApplicationEvent(response, Action.CREATE));
                notice.setText(null);
                view.addToList(response);
                goTo(new ApplicationPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.setText("creating Application . . .");
    }

    public void load(int id) {
        view.setEnabled(false);
        service.show(id, new MethodCallback<Application>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Application: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Application response) {
                eventBus.fireEvent(new ApplicationEvent(response, Action.LOAD));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Application . . .");
        }
    }

    public void save() {
        Application model = view.flush();
        view.setEnabled(false);
        service.update(model, new MethodCallback<Application>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error saving Application: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Application response) {
                eventBus.fireEvent(new ApplicationEvent(response, Action.UPDATE));
                notice.setText(null);
                view.updateInList(response);
                view.edit(response);
                view.reset(place.action);
            }
        });
        notice.setText("saving Application . . .");
    }

    public void delete(final Application model){
        view.setEnabled(false);
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error deleting Application: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Void response) {
                eventBus.fireEvent(new ApplicationEvent(model, Action.DESTROY));
                notice.setText(null);
                view.removeFromList(model);
                ApplicationPlace next = new ApplicationPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.reset(place.action);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.setText("deleting Application . . .");
    }
}