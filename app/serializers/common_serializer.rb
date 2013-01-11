require 'region_serializer'
require 'domain_serializer'
[ RegionSerializer, DomainSerializer ].each do |clazz|
  clazz.add_context(:update,
                    :only => [:id, :name, :updated_at]
                    )

  clazz.add_context(:collection,
                    :except => [:created_at, :modified_by_id]
                    )

  clazz.add_context(:single,
                    :except => [:modified_by_id],
                    :include => {
                      :modified_by => {
                        :only => [:id, :login, :name]
                      }
                    }
                    )
end
