package de.mkristian.ixtlan.users.client.views;


import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.views.ReadOnlyListViewImpl;
import de.mkristian.ixtlan.users.client.errors.Error;
import de.mkristian.ixtlan.users.client.places.ErrorPlace;


@Singleton
public class ErrorListViewImpl extends ReadOnlyListViewImpl<Error> 
            implements ErrorListView {

    @UiTemplate("ListView.ui.xml")
    static interface Binder extends UiBinder<Widget, ErrorListViewImpl> {}

    static private Binder BINDER = GWT.create(Binder.class);

    @Inject
    public ErrorListViewImpl( PlaceController places ) {
        super( "Errors", places );
        initWidget( BINDER.createAndBindUi( this ) );
    }

    @Override
    protected Place place(Error model, RestfulAction action) {
        return new ErrorPlace( model, action );
    }
    //@Override
    public void reset(List<Error> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Message");
        list.setText(0, 2, "Request");
        list.setText(0, 3, "Response");
        list.setText(0, 4, "Session");
        list.setText(0, 5, "Parameters");
        list.setText(0, 6, "Clazz");
        list.setText(0, 7, "Backtrace");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        if (models != null) {
            int row = 1;
            for(Error model: models){
                setRow(row, model);
                row++;
            }
        }
    }

    private void setRow(int row, Error model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getMessage() + "");
        list.setText(row, 2, model.getRequest() + "");
        list.setText(row, 3, model.getResponse() + "");
        list.setText(row, 4, model.getSession() + "");
        list.setText(row, 5, model.getParameters() + "");
        list.setText(row, 6, model.getClazz() + "");
        list.setText(row, 7, model.getBacktrace() + "");

        list.setWidget(row, 8, newButton(SHOW, model));
    }

}
