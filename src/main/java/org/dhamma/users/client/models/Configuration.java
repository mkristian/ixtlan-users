package org.dhamma.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;

@Json(style = Style.RAILS)
public class Configuration implements HasToDisplay {


  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  @Json(name = "idle_session_timeout")
  private int idleSessionTimeout;

  @Json(name = "from_email")
  private String fromEmail;

  public Configuration(){
    this(null, null, null);
  }
  
  @JsonCreator
  public Configuration(@JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy){
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
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

  public int getIdleSessionTimeout(){
    return idleSessionTimeout;
  }

  public void setIdleSessionTimeout(int value){
    idleSessionTimeout = value;
  }

  public String getFromEmail(){
    return fromEmail;
  }

  public void setFromEmail(String value){
    fromEmail = value;
  }

  public String toDisplay() {
    return idleSessionTimeout + "";
  }
}
