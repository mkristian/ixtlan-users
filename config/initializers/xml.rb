class Time
  def xmlschema(options = nil)
    strftime('%Y-%m-%dT%H:%M:%S.') + ("%06d" % usec) + strftime('%z')
  end
end    
class ActiveSupport::TimeWithZone
  def xmlschema(options = nil)
    strftime('%Y-%m-%dT%H:%M:%S.') + ("%06d" % usec) + strftime('%z')
  end
end
class DateTime
  def xmlschema(options = nil)
    strftime('%Y-%m-%dT%H:%M:%S.') + ("%06d" % usec) + strftime('%z')
  end
end
