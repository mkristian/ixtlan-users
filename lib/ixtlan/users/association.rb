module Ixtlan
  module Users
    class Association

      attr_accessor :group_params

      def initialize(model_sym, manager)
        @manager = manager
        name = model_sym.to_s.split('_').collect { |i| i.capitalize }.join
        model = name.respond_to?(:constanize) ? name.constanize : Object.const_get(name)
        name = [User, Group, model].sort { |n,m| n.to_s <=> m.to_s }.join('s')
        # TODO allow namespaces
        @model = name.respond_to?(:constanize) ? name.constanize : Object.const_get(name)
        #name = model_sym.to_s.split('_').collect { |i| i.capitalize }.join
        # @model = name.respond_to?(:constanize) ? name.constanize : Object.const_get(name)
        @id_sym = "#{model_sym}_id".to_sym
        @model_ids = "#{model_sym}_ids".to_sym
      end

      def current_user
        @manager.current_user
      end

      def ids_method(user_id, group_id)
        asso = @model.where(:user_id => user_id, :group_id => group_id)
        asso.collect { |a| a.send @id_sym }
      end

      # [ {:id => 1, :domain_ids => [1,2,3]} ]
      # [ :group => {:id => 1, :domain_ids => [1,2,3]} ]
      def update(user, groups = [])
        group_map = {}
        (groups || []).each do |g|
          group = g[:group] || g
          group_map[group[:id].to_i] = (group[@model_ids] || []).collect { |id| id.to_i }
        end

        user.groups.each do |group|
          allowed_ids = ids_method(current_user.id, group.id)
          if @id_sym == :application_id
            if @manager.current_user.root?
              # all are allowed
              allowed_ids = allowed_ids | (group_map[group.id] || []) | ids_method(user.id, group.id)
            elsif @manager.allowed_applications.size > 0
              # all from the allowed_applications are allowed
              allowed_ids = allowed_ids | @manager.allowed_applications.collect { |a| a.id }
            end
          elsif current_user.root?
            # all are allowed
            allowed_ids = allowed_ids | (group_map[group.id] || []) | ids_method(user.id, group.id)
          end

          current_ids = ids_method(user.id, group.id) & allowed_ids

          target_ids = (group_map[group.id] || []) & allowed_ids

          # delete associations
          (current_ids - target_ids).each do |id|
            return false unless @model.delete_all(["user_id=? and group_id=? and #{@id_sym}=?", user.id, group.id, id])
          end

          # add associations
          (target_ids - current_ids).each do |id|
            return false unless @model.create(:user_id => user.id, :group_id => group.id, @id_sym => id)
          end
        end
        true
      end
    end
  end
end
