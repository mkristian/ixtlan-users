require 'delegate'

class JsonXmlPresenter < SimpleDelegator  

  def initialize(model)
    super(model)
  end

  def self.views
    @views ||= {}
  end

  def self.set_default_view(options = {})
    views[:_default] = options
  end

  def self.add_view(key, options = {})
    views[key.to_sym] = options
  end

  def views
    self.class.views
  end

  def to_model
    __getobj__
  end

#  def class
#    __getobj__.class
#  end

  def method_missing(name, *args, &block)
    if opts = views[name]
      @options = opts
      self
    else
      super
    end
  end
    
  def respond_to?(name)
    views.key?(name) || super
  end
    
  def as_json(options = nil)
    to_model.as_json(_options(options))
  end

  def to_json(options = nil)
    to_model.to_json(_options(options))
  end

  def to_xml(options = nil)
    to_model.to_xml(_options(options))
  end

  private

  def _options(opts)
    if opts
      opts
    else
      @options || views[:_default] || {}
    end
  end
end
