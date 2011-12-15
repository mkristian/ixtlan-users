package org.dhamma.users.client.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import de.mkristian.gwt.rails.editors.DisplayRenderer;
import de.mkristian.gwt.rails.editors.ProvidesId;
import de.mkristian.gwt.rails.editors.ValueCheckBoxes;

public class GroupCheckBoxes extends ValueCheckBoxes<Group> {

    public GroupCheckBoxes() {
        super(new DisplayRenderer<Group>(), new ProvidesId<Group>());
    }

    private final Map<Integer, GroupPanel> panels = new HashMap<Integer, GroupPanel>();
    private List<Application> apps = new ArrayList<Application>();
    
    static class GroupPanel extends FlowPanel implements CheckBoxItem {
        private final ListBox applications = new ListBox(true);
        private final CheckBoxItem box;
        private Group group;
        
        GroupPanel(CheckBoxItem box, Group group, List<Application> apps){
            this.group = group;
            this.box = box;
            box.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    adjustListBox(event.getValue());
                }
            });
            
            add(box);
            add(new Label("applications"));
            add(applications);
            if(apps != null && !apps.isEmpty()){
                applications.clear();
                for(Application a: apps){
                    applications.addItem(a.toDisplay(), a.getId() + "");
                }
            }
            else {
                applications.addItem("loading . . .");
            }
            applications.setEnabled(false);
        }
        
        void resetApplications(List<Application> apps){
            if(apps != null && !apps.isEmpty()){
                applications.clear();
                for(Application a: apps){
                    applications.addItem(a.toDisplay(), a.getId() + "");
                }
                for(int index = 0; index < applications.getItemCount(); index++){
                    int id = Integer.parseInt(applications.getValue(index));
                    applications.setItemSelected(index, group.getApplicationIds().contains(id));
                }
            }
            adjustListBox(box.getValue());
        }

        public List<Integer> getSelectedApplicationIds() {
            if (applications.getItemCount() == 0){
                return group.getApplicationIds();
            }
            List<Integer> result = new ArrayList<Integer>(applications.getItemCount());
            for(int index = 0; index < applications.getItemCount(); index++){
                if(applications.isItemSelected(index)){
                    int id = Integer.parseInt(applications.getValue(index));
                    result.add(id);
                }
            }
            return result;
        }

        public void setApplicationIds(List<Integer> applicationIds) {
            for(int index = 0; index < applications.getItemCount(); index++){
                int id = Integer.parseInt(applications.getValue(index));
                applications.setItemSelected(index, applicationIds.contains(id));
            }
        }

        public Boolean getValue() {
            return box.getValue();
        }

        public void setValue(Boolean value) {
            box.setValue(value);
            adjustListBox(value);
        }
        
        private void adjustListBox(boolean value) {
            applications.setEnabled(value);
            if (!value) {
                for(int index = 0; index < applications.getItemCount(); index++){
                    applications.setItemSelected(index, false);
                }
            }
        }

        public void setValue(Boolean value, boolean fireEvents) {
            box.setValue(value, fireEvents);
            adjustListBox(value);
        }

        public HandlerRegistration addValueChangeHandler(
                ValueChangeHandler<Boolean> handler) {
            return box.addValueChangeHandler(handler);
        }

        public void setEnabled(boolean enabled) {
            box.setEnabled(enabled);
            applications.setEnabled(enabled);
        }

        public Object getKey() {
            return box.getKey();
        }

        public void setGroup(Group g) {
            this.group = g;
            setApplicationIds(g.getApplicationIds());
        }
    }
    
    @Override
    protected CheckBoxItem newItem(Group group, Object key) {
        CheckBoxItem box = super.newItem(group, key);
        if (group.hasApplications()){
            GroupPanel panel = new GroupPanel(box, group, apps);
            panels.put(group.getId(), panel);
            return panel;
        }
        else {
            return box;
        }
    }
    
    public void resetApplications(List<Application> apps){
        this.apps = apps;
        if (apps != null){
            for(GroupPanel panel: panels.values()){
                panel.resetApplications(apps);
            }
        }
    }

    @Override
    public void setValue(List<Group> groups, boolean fireEvents) {
        List<Group> before = this.selectedValues;
        super.setValue(groups, false);

        if (groups != null) {
            for(Group g: groups){
                GWT.log(g.getApplicationIds().toString());
                GroupPanel panel = panels.get(g.getId());
                if (panel != null) {
                    panel.setGroup(g);
                }
            }
        }
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, before, groups);
        }
    }

    @Override
    public List<Group> getValue() {
        List<Group> groups = super.getValue();
        for(Group g: groups){
            GroupPanel panel = panels.get(g.getId());
            if (panel != null) {
                g.setApplicationIds(panel.getSelectedApplicationIds());
            }
        }
        return groups;
    }
}
