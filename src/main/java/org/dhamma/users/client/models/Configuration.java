package org.dhamma.users.client.models;


import java.util.Date;

import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

@Json(style = Style.RAILS)
public class Configuration {


  @Json(name = "idle_session_timeout")
  public int idleSessionTimeout;

  @Json(name = "password_from_email")
  public String passwordFromEmail;

  @Json(name = "login_url")
  public String loginUrl;


  @Json(name = "created_at")
  public Date createdAt;

  @Json(name = "updated_at")
  public Date updatedAt;
}