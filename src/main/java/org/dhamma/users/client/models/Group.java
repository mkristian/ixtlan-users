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

@Json(style = Style.RAILS)
public class Group implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  private String name;

  private String description;

  @Json(name = "application_id")
  private int applicationId;
  private Application application;

  @Json(name = "application_ids")
  private final List<Integer> applicationIds;
  private List<Application> applications;

  private final boolean hasApplications;
  
  @Json(name = "has_regions")
  private boolean hasRegions;

  @Json(name = "region_ids")
  private final List<Integer> regionIds;
  private List<Region> regions;

  public Group(){
      this(0, null, null, null, 0, null, null);
  }

  public Group(int id){
      this(id, null, null, null, 0, null, null);
    }
  
  @JsonCreator
  public Group(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy,
          @JsonProperty("applicationId") int applicationId,
          @JsonProperty("applicationIds") List<Integer> applicationIds,
          @JsonProperty("regionIds") List<Integer> regionIds){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
    this.applicationId = applicationId;
    this.hasApplications = applicationIds != null;
    this.applicationIds = applicationIds == null ? new ArrayList<Integer>() : applicationIds;
    this.regionIds = regionIds == null ? new ArrayList<Integer>() : regionIds;
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

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public String getDescription(){
    return description == null ? "" : description;
  }

  public void setDescription(String value){
    description = value;
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

  public boolean hasApplications() {
    return hasApplications;
  }

  public List<Application> getApplications() {
    return applications;
  }

  public List<Integer> getApplicationIds() {
    return applicationIds;
  }

  public void setApplications(List<Application> applications) {
    this.applications = applications;
    updateApplicationIds(applications);
  }

  private void updateApplicationIds(List<Application> applications) {
    this.applicationIds.clear();
    if (applications != null){
        for(Application a: applications){
            this.applicationIds.add(a.getId());
        }
    }
  }
  
  public boolean hasRegions(){
      return hasRegions;
  }

  public boolean getHasRegions(){
      return hasRegions;
  }

  public void setHasRegions(boolean value){
      hasRegions = value;
  }

  public List<Region> getRegions() {
    return regions;
  }

  public List<Integer> getRegionIds() {
    return regionIds;
  }

  public void setRegions(List<Region> regions) {
    this.regions = regions;
    updateRegionIds(regions);
  }

  private void updateRegionIds(List<Region> regions) {
    this.regionIds.clear();
    if (regions != null){
        for(Region a: regions){
            this.regionIds.add(a.getId());
        }
    }
  }

  public Group minimalClone() {
      Group clone = new Group(id, null, updatedAt, null, applicationId, applicationIds, regionIds);
      clone.setName(this.name);
      clone.setDescription(this.description);
      clone.setHasRegions(this.hasRegions);
      return clone;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Group) && 
        ((Group)other).id == id;
  }

  public String toDisplay() {
    return name + (application == null ? "" : "@" + application.getName());
  }

  public void setApplicationIds(List<Integer> applicationIds) {
      this.applications = null;
      this.applicationIds.clear();
      this.applicationIds.addAll(applicationIds);
  }

  public boolean hasAssociations() {
    return hasApplications || hasRegions;
  }

  public void setRegionIds(List<Integer> regionIds) {
    this.regions = null;
    this.regionIds.clear();
    this.regionIds.addAll(regionIds);
  }
}
