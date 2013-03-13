require 'ixtlan/babel/context'

module Ixtlan
  module Babel
    class Context


      def []( key )
        if @include.include?( key )
          self.class.new( @include.is_a?( Array ) ? {} : @include[ key ] )
        else
          self.class.new( @methods.is_a?( Array ) ? {} : @methods[ key ] )
        end
      end

    end
  end
end
