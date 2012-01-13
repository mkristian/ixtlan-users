require "spec_helper"

describe RemotePermissionsController do
  describe "routing" do

    it "routes to #index" do
      get("/remote_permissions").should route_to("remote_permissions#index")
    end

    it "routes to #new" do
      get("/remote_permissions/new").should route_to("remote_permissions#new")
    end

    it "routes to #show" do
      get("/remote_permissions/1").should route_to("remote_permissions#show", :id => "1")
    end

    it "routes to #edit" do
      get("/remote_permissions/1/edit").should route_to("remote_permissions#edit", :id => "1")
    end

    it "routes to #create" do
      post("/remote_permissions").should route_to("remote_permissions#create")
    end

    it "routes to #update" do
      put("/remote_permissions/1").should route_to("remote_permissions#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/remote_permissions/1").should route_to("remote_permissions#destroy", :id => "1")
    end

  end
end
