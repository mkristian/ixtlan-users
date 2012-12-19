require 'acceptance/acceptance_helper'
require 'rails_gwt/dsl'

MENU = %w(Ats Regions Audits Profile Configuration Errors Applications Groups Users Remote\ permissions)

CONFIG = {
  :root => {
    :name => 'Root',
    :menu => MENU,
    :users => {
      :new => { :action_buttons => 'Create', :buttons => 'Search' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show", 'Search'] },
      :show => { :buttons => ['New', 'Edit', 'Search'] },
      :index => { :buttons => 'New', :content => 'root@example.com' }
    },
    :regions => {
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => 'Asia' }
    },
    :remote_permissions => {
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => 'development' }
    },
    :applications => {
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => 'development' }
    },
    :groups => {
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], 
        :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => 'user-admin' }
    },
    :ats => {
      :resource_id => "User.find_by_login('at').id",
      :show => { :buttons => ['New', 'Edit', 'Search'] },
      :index => { :buttons => 'New', :content => 'at@example.com' }
    },
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    }
  },
  :root_dev => {
    :name => 'Root',
    :menu => ["Applications", "Groups", "Profile", "Remote permissions", "Users"],
    :users => {
      :show => { :buttons => ['Edit', 'Search'] },
      :index => { :content => ['registrar@example.com', 'root_dev@example.com'] }
    },
    :applications => {
      :resource_id => "Application.find_by_name('development').id",
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' },
      :index => { :content => 'development' }
    },
    :remote_permissions => {
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => 'development' }
    },
    :groups => {
      :resource_id => "Group.find_by_name('registrar').id",
      :new => { :action_buttons => 'Create' },
      :edit => { :action_buttons => ['Save', 'Delete'], 
        :buttons => ["New", "Show"] },
      :show => { :buttons => ['New', 'Edit'] },
      :index => { :buttons => 'New', :content => ['registrar', 'root'] }
    },
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    }
  },
  :none => {
    :name => 'None',
    :menu => "Profile",
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    }
  },
  :registrar => {
    :name => 'Registrar',
    :menu => "Profile",
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    }
  },
  :user_admin => {
    :name => 'User Admin',
    :menu => ["Profile", "Users"],
    :users => {
      :new => { :action_buttons => 'Create', :buttons => 'Search' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show", 'Search'] },
      :show => { :buttons => ['New', 'Edit', 'Search'] },
      :index => { :buttons => 'New', :content => 'root@example.com' }
    },
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    }
  },
  :at => {
    :name => 'AT',
    :menu => ["Profile", 'Ats'],
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    },
    :ats => {
      :resource_id => "User.find_by_login('at').id",
      :show => { :buttons => ['Search'] },
      :index => { :content => 'at@example.com' }
    },
  },
  :admin_at => {
    :name => 'AT Admin',
    :menu => ["Profile", 'Ats', 'Users'],
    :profile => {
      :mode => :singleton,
      :edit => { :action_buttons => 'Save', :buttons => "Show" },
      :show => { :buttons => 'Edit' }
    },
    :users => {
      :new => { :action_buttons => 'Create', :buttons => 'Search' },
      :edit => { :action_buttons => ['Save', 'Delete'], :buttons => ["New", "Show", 'Search'] },
      :show => { :buttons => ['New', 'Edit', 'Search'] },
      :index => { :buttons => 'New', :content => 'root@example.com' }
    },
    :ats => {
      :resource_id => "User.find_by_login('at').id",
      :show => { :buttons => ['New', 'Edit', 'Search'] },
      :index => { :buttons => 'New', :content => 'at@example.com' }
    },
  }
}

FACTORY = RailsGwt::UserConfigFactory.new

CONFIG.each do |login, config|
  FACTORY.add(login, config)
end

feature "GUI permissions", %q{
just test the UI to show the right buttons after login with different users
} do

  include RailsGwt::DSL

  background do
    # regions
    europe = Region.find_by_name("Europe")
    unless europe
      europe = Region.create(:name => "Europe", :modified_by => User.first)
    end
    asia = Region.find_by_name("Asia")
    unless asia
      asia = Region.create(:name => "Asia", :modified_by => User.first)
    end

    # registrar group
    registrar_group = Group.find_by_name("registrar")
    unless registrar_group
      registrar_group = Group.create(:name => "registrar", :has_regions=> true, :modified_by => User.first, :application => Application.find_by_name("development"))
    end

    #users
    unless User.find_by_login("none")
      User.create(:login => "none", :email => "none@example.com", :name => "None", :modified_by => User.first)
    end
    
    unless at = User.find_by_login("at")
      at = User.create(:login => "at", :email => "at@example.com", :name => "AT", :modified_by => User.first, :at_token => 'asd')
      at.groups << Group.AT
      at.save
    end

    unless user_admin = User.find_by_login("user_admin")
      user_admin = User.create(:login => "user_admin", :email => "user_admin@example.com", :name => "User Admin", :modified_by => User.first)
      user_admin.groups << Group.USER_ADMIN
      user_admin.save
    end

    unless admin_at = User.find_by_login("admin_at")
      admin_at = User.create(:login => "admin_at", :email => "admin_at@example.com", :name => "AT Admin", :modified_by => User.first, :at_token => 'asd')
      admin_at.groups << Group.AT
      admin_at.groups << Group.USER_ADMIN
      admin_at.save
    end

    unless registrar_user = User.find_by_login("registrar")
      registrar_user = User.create(:login => "registrar", :email => "registrar@example.com", :name => "Registrar", :modified_by => User.first)
      registrar_user.groups << registrar_group
      registrar_user.save
      GroupsRegionsUser.create(:group => registrar_group, :region => asia, :user => registrar_user)
    end

    unless root_dev = User.find_by_login("root_dev")
      root_dev = User.create(:login => "root_dev", :email => "root_dev@example.com", :name => "Root Development", :modified_by => User.first)
      root_dev.groups << Group.where(:name => 'root', :application_id => Application.find_by_name('development').id).first
      root_dev.groups << Group.APP_ADMIN
      root_dev.save     
      ApplicationsGroupsUser.create(:application => Application.find_by_name('development'),:group => Group.APP_ADMIN, :user => root_dev)

    end
  end

  LOGINS = FACTORY.logins
  #LOGINS = [:root_dev, :root, :user_admin,:none, :at, :registrar, :admin_at]
  LOGINS.shuffle.each do |login|
    scenario "pages with '#{login}'" do
      visit '/Users.html#profiles'

      login_session(login) do #, false) do
        userconfig = FACTORY[login]
        userconfig.resources.each do |resource|
          userconfig.resource = resource
          visit_resource(userconfig) do |config|
            # should have all search dropdown boxes filled
            find(:xpath, '//select[@name="group" or @name = "application"]').should have_no_content("loading") if config.resource == :users
          end
        end
      end
    end
  end
end

