require 'ixtlan/users/association'
module Ixtlan
  module Users
    class Manager

      attr_accessor :group_params

      attr_reader :current_user

      def initialize(current_user, *models)
        @current_user = current_user
        models.each do |model|
          associations << Association.new(model, self)
        end
      end

      def associations
        @association ||= []
      end

      def group_ids(user, params = nil)
        params ||= {}
        @groups = params.delete(:groups)
        if @groups
          # we have groups so ignore the group_ids
          params.delete(:group_ids)
          # and use the group_ids from the group model
          group_ids = @groups.collect { |g| (g[:group] || g)[:id].to_i }
          group_ids = new_group_ids(user, :group_ids => group_ids)
          # adjust the groups to the allowed groups
          @groups.delete_if do |g|
            !group_ids.member?((g[:group] || g)[:id].to_i)
          end
        else
          group_ids = (params.delete(:group_ids) || []).collect { |g| g.to_i }
          group_ids = new_group_ids(user, :group_ids => group_ids)
          # adjust the groups to the allowed groups
          @groups = group_ids.collect do |g|
            #TODO fixnum or group - are realy both possible ?
            { :id => g.is_a?(Fixnum) ? g : g.id }
          end
        end
        # TODO since we have already @groups as state we could return
        # only the groupids
        group_ids
      end

      def update(user, groups = nil)
        groups ||= (@groups || [])
        # update first the user and then the associations for each group
        associations.all? do |a|
          a.update(user, groups)
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

      def all_group_ids
        @all_group_ids ||= Group.where(:application_id => allowed_applications).collect { |g| g.id }
      end

      def allowed_applications
        @apps ||= @current_user.allowed_applications
      end

      def allowed_group_ids(params)
        if current_user.root?
          # all are allowed
          requested_group_ids(params)
        elsif allowed_applications.empty?
          # only the ones from the current_user.groups are allowed
          current_group_ids & requested_group_ids(params)
        else
          # only the groups which are bound to the application are allowed
          all_group_ids & requested_group_ids(params)
        end
      end

      def new_group_ids(user, params)
        if current_user.root?
          requested_group_ids(params)
        else
          if user
            # groups of the user as in database
            user_group_ids = user.groups.collect do |g|
              g.id
            end
            if allowed_applications.empty?
              # the new set of group ids for the given user
              user_group_ids - current_group_ids + allowed_group_ids(params)
            else
              # the new set of group ids for the given user
              user_group_ids - all_group_ids + allowed_group_ids(params)
            end
          else
            allowed_group_ids(params)
          end
        end
      end
    end
  end
end
