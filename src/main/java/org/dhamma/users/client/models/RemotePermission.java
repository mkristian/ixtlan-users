package org.dhamma.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS, name = "remote_permission")
public class RemotePermission implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  private String ip;

  private String token;

  @Json(name = "application_id")
  private int applicationId;
  private Application application;

  public RemotePermission(){
    this(0, null, null, null, 0);
  }
  
  @JsonCreator
  public RemotePermission(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy,
          @JsonProperty("applicationId") int applicationId){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
    this.applicationId = applicationId;
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

  public String getIp(){
    return ip;
  }

  public void setIp(String value){
    ip = value;
  }

  public String getToken(){
    return token;
  }

  public void setToken(String value){
    token = value;
  }

  public Application getApplication(){
    return application;
  }

  public void setApplication(Application value){
    application = value;
    applicationId = value == null ? 0 : value.getId();
  }

  public int getApplicationId(){
    return applicationId;
  }

  public RemotePermission minimalClone() {
      RemotePermission clone = new RemotePermission(id, null, updatedAt, null, applicationId);
      clone.setIp(this.ip);
      clone.setToken(this.token);
      return clone;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof RemotePermission) && 
        ((RemotePermission)other).id == id;
  }

  public String toDisplay() {
    return ip;
  }
}
