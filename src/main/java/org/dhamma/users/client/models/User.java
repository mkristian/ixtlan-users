package org.dhamma.users.client.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;
import de.mkristian.gwt.rails.models.IsUser;

@Json(style = Style.RAILS)
public class User implements HasToDisplay, Identifyable, IsUser {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private User modifiedBy;

  private String login;

  private String email;

  private String name;

  @Json(name = "group_ids")
  private final List<Integer> groupIds;
  private List<Group> groups;

  private transient List<Group> hiddenGroups;

  private transient User hiddenModifiedBy;
  
  public User(){
    this(0, null, null, null, null);
  }
  
  @JsonCreator
  public User(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy,
          @JsonProperty("groupIds") List<Integer> groupIds){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
    this.groupIds = groupIds == null ? new ArrayList<Integer>(): groupIds;
  }

  public int getId(){
    return id;
  }

  public Date getCreatedAt(){
    return createdAt;
  }

  public Date getUpdatedAt(){
    return updatedAt;
  }

  public User getModifiedBy(){
    return modifiedBy;
  }

  public String getLogin(){
    return login;
  }

  public void setLogin(String value){
    login = value;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String value){
    email = value;
  }

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public List<Integer> getGroupIds() {
    return groupIds;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
    updateGroupIds(groups);
  }

  private void updateGroupIds(List<Group> groups) {
    this.groupIds.clear();
    for(Group g: groups){
        this.groupIds.add(g.getId());
    }
  }

  public void hideNested(){
      this.hiddenModifiedBy = this.modifiedBy;
      this.modifiedBy = null;
      this.hiddenGroups = this.groups;
      this.groups = null;
      updateGroupIds(this.hiddenGroups);
   }
  
  public void unhideNested(){
      this.modifiedBy = this.hiddenModifiedBy;
      this.hiddenModifiedBy = null;
      this.groups = this.hiddenGroups;
      this.hiddenGroups = null;
  }
  
  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof User) && 
        ((User)other).id == id;
  }

  public String toDisplay() {
    return login;
  }
}
