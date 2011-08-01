package org.dhamma.users.client.views;

import java.util.ArrayList;

import de.mkristian.gwt.rails.ColumnDefinition;
import org.dhamma.users.client.models.User;

@SuppressWarnings("serial")
public class UsersColumnDefinitionsImpl extends 
    ArrayList<ColumnDefinition<User>> {
  
  protected UsersColumnDefinitionsImpl() {
    
    this.add(new ColumnDefinition<User>() {
      public void render(User c, StringBuilder sb) {        
        sb.append("<div id='" + c.id + "'>" + "TODO" + "</div>");
      }

      public boolean isClickable() {
        return true;
      }
    });
  }
}