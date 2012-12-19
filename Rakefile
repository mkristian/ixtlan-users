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

task :headers do
  require 'rubygems'
  require 'copyright_header'
 
  args = {
    :license => 'AGPL3', 
    :copyright_software => 'ixtlan_users',
    :copyright_software_description => 'webapp to manage users for other webapps/miniapps',
    :copyright_holder => ['Christian Meier'],
    :copyright_years => [Time.now.year],
    :add_path => ['lib', 'app', 'src', 'config', 'db/seeds.rb'].join(File::SEPARATOR),
    :output_dir => '.'
  }

  command_line = CopyrightHeader::CommandLine.new( args )
  command_line.execute
end
# vim: syntax=Ruby
