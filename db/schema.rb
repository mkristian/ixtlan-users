# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20130110161047) do

  create_table "applications", :force => true do |t|
    t.string   "name"
    t.string   "url"
    t.datetime "created_at",           :null => false
    t.datetime "updated_at",           :null => false
    t.integer  "modified_by_id"
    t.string   "allowed_ip"
    t.string   "authentication_token"
  end

  create_table "applications_groups_users", :id => false, :force => true do |t|
    t.integer "application_id"
    t.integer "group_id"
    t.integer "user_id"
  end

  add_index "applications_groups_users", ["application_id", "group_id", "user_id"], :name => "index_applications_groups_users", :unique => true

  create_table "audits", :force => true do |t|
    t.string   "login"
    t.string   "path"
    t.string   "message"
    t.datetime "created_at",  :null => false
    t.string   "http_method"
  end

  create_table "configurations", :force => true do |t|
    t.integer  "idle_session_timeout", :default => 15
    t.string   "from_email"
    t.datetime "created_at",                           :null => false
    t.datetime "updated_at",                           :null => false
    t.integer  "modified_by_id"
    t.integer  "errors_keep_dumps",    :default => 30
    t.string   "errors_base_url"
    t.string   "errors_from_email"
    t.string   "errors_to_emails"
    t.integer  "audits_keep_logs",     :default => 90
    t.string   "profile_url"
    t.string   "ats_url"
  end

  create_table "domains", :force => true do |t|
    t.string   "name"
    t.datetime "created_at",     :null => false
    t.datetime "updated_at",     :null => false
    t.integer  "modified_by_id"
  end

  create_table "domains_groups_users", :id => false, :force => true do |t|
    t.integer "domain_id"
    t.integer "group_id"
    t.integer "user_id"
  end

  add_index "domains_groups_users", ["domain_id", "group_id", "user_id"], :name => "index_domains_groups_users_on_domain_id_and_group_id_and_user_id", :unique => true

  create_table "errors", :force => true do |t|
    t.string   "message"
    t.text     "request"
    t.text     "response"
    t.text     "session"
    t.text     "parameters"
    t.string   "clazz"
    t.text     "backtrace"
    t.datetime "created_at", :null => false
  end

  create_table "groups", :force => true do |t|
    t.string   "name"
    t.integer  "application_id"
    t.datetime "created_at",                        :null => false
    t.datetime "updated_at",                        :null => false
    t.integer  "modified_by_id"
    t.boolean  "has_regions",    :default => false
    t.boolean  "has_locales",    :default => false
    t.boolean  "has_domains",    :default => false
  end

  create_table "groups_locales_users", :id => false, :force => true do |t|
    t.integer "group_id"
    t.integer "locale_id"
    t.integer "user_id"
  end

  add_index "groups_locales_users", ["group_id", "locale_id", "user_id"], :name => "index_groups_locales_users_on_group_id_and_locale_id_and_user_id", :unique => true

  create_table "groups_regions_users", :id => false, :force => true do |t|
    t.integer "group_id"
    t.integer "region_id"
    t.integer "user_id"
  end

  add_index "groups_regions_users", ["group_id", "region_id", "user_id"], :name => "index_groups_regions_users_on_group_id_and_region_id_and_user_id", :unique => true

  create_table "groups_users", :id => false, :force => true do |t|
    t.integer "group_id"
    t.integer "user_id"
  end

  add_index "groups_users", ["group_id", "user_id"], :name => "index_groups_users_on_group_id_and_user_id", :unique => true

  create_table "locales", :force => true do |t|
    t.string   "code"
    t.datetime "updated_at"
  end

  create_table "regions", :force => true do |t|
    t.string   "name"
    t.datetime "created_at",     :null => false
    t.datetime "updated_at",     :null => false
    t.integer  "modified_by_id"
  end

  create_table "sessions", :force => true do |t|
    t.string   "session_id", :null => false
    t.text     "data"
    t.datetime "created_at", :null => false
    t.datetime "updated_at", :null => false
  end

  add_index "sessions", ["session_id"], :name => "index_sessions_on_session_id"
  add_index "sessions", ["updated_at"], :name => "index_sessions_on_updated_at"

  create_table "users", :force => true do |t|
    t.string   "login"
    t.string   "email"
    t.string   "name"
    t.string   "hashed"
    t.string   "hashed2"
    t.datetime "created_at",     :null => false
    t.datetime "updated_at",     :null => false
    t.integer  "modified_by_id"
    t.string   "at_token"
  end

end
