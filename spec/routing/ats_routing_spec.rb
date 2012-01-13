require "spec_helper"

describe AtsController do
  describe "routing" do

    it "routes to #index" do
      get("/ats").should route_to("ats#index")
    end

    it "routes to #new" do
      get("/ats/new").should route_to("ats#new")
    end

    it "routes to #show" do
      get("/ats/1").should route_to("ats#show", :id => "1")
    end

    it "routes to #edit" do
      get("/ats/1/edit").should route_to("ats#edit", :id => "1")
    end

    it "routes to #create" do
      post("/ats").should route_to("ats#create")
    end

    it "routes to #update" do
      put("/ats/1").should route_to("ats#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/ats/1").should route_to("ats#destroy", :id => "1")
    end

  end
end
