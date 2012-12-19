require 'ixtlan/babel/hash_filter'

class ParamsFilter < Ixtlan::Babel::HashFilter
  
  attr_reader :updated_at, :params
  
  def initialize
    super
    options = { :except => [ :id, 
                             :created_at, 
                             :updated_at, 
                             :modified_by_id,
                             :application_ids,
                             :region_ids,
                             :locale_ids,
                             :domain_ids
                           ] }
  end
  
  def filter(params)
    if params
      @updated_at = params[:updated_at]
      @params = super
    end
  end
end
