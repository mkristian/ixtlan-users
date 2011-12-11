require 'ixtlan/users/association'
module Ixtlan
  module Users
    class Manager

      attr_accessor :group_params

      def initialize(current_user, *models)
        @current_user = current_user
        models.each do |model|
          associations << Association.new(@current_user, model)
        end
      end

      def associations
        @association ||= []
      end

      def update(user, params = (group_params || {}))
        associations.all? do |a|
          a.update(user, params)
        end
      end

      def current_group_ids
        @current_group_ids ||= @current_user.groups.collect do |g|
          g.id
        end
      end
      
      def requested_group_ids(params)
        (params[:group_ids] || []).collect { |id| id.to_i }
      end
      
      def allowed_group_ids(params)
        if @current_user.root?
          requested_group_ids(params)
        else
          self.class.intersect(current_group_ids, requested_group_ids(params))
        end
      end
      
      def new_group_ids(user, params)
        # groups of the user as in database
        user_group_ids = user.groups.collect do |g|
          g.id
        end
        if @current_user.root?
          self.class.union(user_group_ids, requested_group_ids(params))
        else
          # the new set of group ids for the given user
          user_group_ids - current_group_ids + allowed_group_ids(params)
        end
      end
      
      def self.intersect(set1, set2)
        set1 - (set1 - set2)
      end
      
      def self.union(set1, set2)
        (set1 - set2) + set2
      end
    end
  end
end
