class Group < ActiveRecord::Base
  belongs_to :application
  belongs_to :modified_by, :class_name => "User"
  validates :modified_by_id, :presence => true

  attr_accessor :applications

  def self.ROOT
    find_by_id(1) || new(:name => 'root', :application => Application.THIS)
  end

  def self.USER_ADMIN
    find_by_id(2) || new(:name => 'user-admin', :application => Application.THIS)
  end

  def self.AT
    find_by_id(3) || new(:name => 'at', :application => Application.ALL)
  end

  def self.options
    {
      :except => [:created_at, :updated_at, :modified_by_id],
      :include => {
        :application => {
          :only => [:id, :name]
        }
      }
    }
  end

  def self.single_options
    {
      :except => [:modified_by_id],
      :include => {
        :modified_by => {
          :only => [:id, :login, :name]
        },
        :application => {
          :except => [:created_at, :updated_at, :modified_by_id]
        }
      }
    }
  end
end
