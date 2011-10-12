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

  @Json(name = "password_from_email")
  private String passwordFromEmail;

  @Json(name = "login_url")
  private String loginUrl;

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

  public String getPasswordFromEmail(){
    return passwordFromEmail;
  }

  public void setPasswordFromEmail(String value){
    passwordFromEmail = value;
  }

  public String getLoginUrl(){
    return loginUrl;
  }

  public void setLoginUrl(String value){
    loginUrl = value;
  }

  public String toDisplay() {
    return "configuration";
  }
}
