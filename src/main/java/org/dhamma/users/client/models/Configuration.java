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

  @Json(name = "errors_keep_dumps")
  private int errorsKeepDumps;

  @Json(name = "errors_base_url")
  private String errorsBaseUrl;

  @Json(name = "errors_from_email")
  private String errorsFromEmail;

  @Json(name = "errors_to_emails")
  private String errorsToEmails;

  @Json(name = "idle_session_timeout")
  private int idleSessionTimeout;

  @Json(name = "audits_keep_logs")
  private int auditsKeepLogs;

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

  public int getErrorsKeepDumps(){
    return errorsKeepDumps;
  }

  public void setErrorsKeepDumps(int value){
    errorsKeepDumps = value;
  }

  public String getErrorsBaseUrl(){
    return errorsBaseUrl;
  }

  public void setErrorsBaseUrl(String value){
    errorsBaseUrl = value;
  }

  public String getErrorsFromEmail(){
    return errorsFromEmail;
  }

  public void setErrorsFromEmail(String value){
    errorsFromEmail = value;
  }

  public String getErrorsToEmails(){
    return errorsToEmails;
  }

  public void setErrorsToEmails(String value){
    errorsToEmails = value;
  }

  public int getIdleSessionTimeout(){
    return idleSessionTimeout;
  }

  public void setIdleSessionTimeout(int value){
    idleSessionTimeout = value;
  }

  public int getAuditsKeepLogs(){
    return auditsKeepLogs;
  }

  public void setAuditsKeepLogs(int value){
    auditsKeepLogs = value;
  }

  public String getFromEmail(){
    return fromEmail;
  }

  public void setFromEmail(String value){
    fromEmail = value;
  }

  public String toDisplay() {
    return errorsKeepDumps + "";
  }
}
