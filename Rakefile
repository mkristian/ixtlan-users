#-*- mode: ruby -*-

require File.expand_path('../config/application', __FILE__)
require 'rake'
require 'updater' # needed here - expected rails does this for me

Users::Application.load_tasks

desc 'triggers the update of remote resources'
task :update => [:environment] do
    sync = Updater.new
    sync.do_it

    puts "#{Time.now.strftime('%Y-%m-%d %H:%M:%S')}\n\t#{sync}"
end
# vim: syntax=Ruby
