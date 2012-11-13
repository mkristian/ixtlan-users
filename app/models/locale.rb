class Locale < ActiveRecord::Base
  def to_s
    "Locale(#{code})"
  end
end
