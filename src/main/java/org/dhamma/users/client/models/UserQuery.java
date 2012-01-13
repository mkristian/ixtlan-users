package org.dhamma.users.client.models;

import java.util.List;

import de.mkristian.gwt.rails.caches.FilterUtils;
import de.mkristian.gwt.rails.models.AbstractQuery;

public class UserQuery extends AbstractQuery<User> {

    private String name;
   
    private Group group;
    
    private Application application;
      
    public UserQuery(String query){
        super(query);
    }   

    public List<User> filter(List<User> models) {
        if (name == null 
                && (group == null || group.getId() == 0)
                && (application == null || application.getId() == 0)) {
            return models;
        }
        return super.filter(models);
    }
    
    @Override
    protected boolean match(User user) {
        return (name == null || user.searchToken().contains(name))
                && (group == null || group.getId() == 0 || user.getGroupIds().contains(group.getId()))
                && (application == null || application.getId() == 0 || user.getApplicationIds().contains(application.getId()));
    }

    public void setName(String value) {
        this.name = value == null ? value : FilterUtils.normalize(value);
    }

    public String getName() {
        return this.name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String toQuery() {
        StringBuilder buf = new StringBuilder(this.name == null ? "" : this.name);
        buf.append(SEPARATOR).append(group == null ? 0 : group.getId());
        buf.append(SEPARATOR).append(application == null ? 0 : application.getId());
        return buf.toString();
    }
    
    public void fromQuery(String query){
        if (query != null){
            String[] parts = query.split("[" + SEPARATOR + "]", -1);
            setName(parts[0]);
            setGroup(new Group(Integer.parseInt(parts[1])));
            setApplication(new Application(Integer.parseInt(parts[2])));
        }
    }
}