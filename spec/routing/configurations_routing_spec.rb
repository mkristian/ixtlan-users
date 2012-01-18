require "spec_helper"

describe ConfigurationsController do
  describe "routing" do

    it "routes to #new" do
      get("/configurations/new").should route_to("configurations#new")
    end

    it "routes to #show" do
      get("/configurations/1").should route_to("configurations#show", :id => "1")
    end

    it "routes to #edit" do
      get("/configurations/1/edit").should route_to("configurations#edit", :id => "1")
    end

    it "routes to #create" do
      post("/configurations").should route_to("configurations#create")
    end

    it "routes to #update" do
      put("/configurations/1").should route_to("configurations#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/configurations/1").should route_to("configurations#destroy", :id => "1")
    end

  end
end
