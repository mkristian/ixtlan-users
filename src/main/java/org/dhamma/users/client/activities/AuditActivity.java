package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.AuditEvent;
import org.dhamma.users.client.models.Audit;
import org.dhamma.users.client.places.AuditPlace;
import org.dhamma.users.client.restservices.AuditsRestService;
import org.dhamma.users.client.views.AuditView;

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

public class AuditActivity extends AbstractActivity implements AuditView.Presenter{

    private final AuditPlace place;
    private final AuditsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final AuditView view;
    private EventBus eventBus;
    
    @Inject
    public AuditActivity(@Assisted AuditPlace place, final Notice notice, final AuditView view,
            AuditsRestService service, PlaceController placeController) {
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

        switch(RestfulActionEnum.valueOf(place.action)){
            case SHOW:
                load(place.id);
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
        service.index(new MethodCallback<List<Audit>>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error loading list of Audit", exception);
            }

            public void onSuccess(Method method, List<Audit> response) {
                notice.finishLoading();
                eventBus.fireEvent(new AuditEvent(response, Action.LOAD));
                view.reset(response);
            }
        });
        notice.loading();
    }

    public void load(int id) {
        view.edit(new Audit()); // clear the form
        service.show(id, new MethodCallback<Audit>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error loading Audit", exception);
            }

            public void onSuccess(Method method, Audit response) {
                notice.finishLoading();
                eventBus.fireEvent(new AuditEvent(response, Action.LOAD));
                view.edit(response);
            }
        });
        notice.loading();
    }
}