#-*- mode: ruby -*-

jar('org.jruby.rack:jruby-rack', '1.1.13.1').exclusions << 'org.jruby:jruby-complete'
if defined?( JRUBY_VERSION ) && JRUBY_VERSION =~ /^1\.7\./
  jar("org.jruby:jruby-core", "${jruby.version}")
else
  jar("org.jruby:jruby-complete", "${jruby.version}")
end
jar('com.google.gwt:gwt-user', '${gwt.version}').scope :provided 

jar('de.mkristian.gwt:rails-gwt', '0.8.0-SNAPSHOT').scope :provided
jar('org.fusesource.restygwt:restygwt', '1.3').scope :provided
jar('javax.ws.rs:jsr311-api', '1.1').scope :provided
jar('com.google.gwt.inject:gin', '2.0.0').scope :provided
jar('javax.validation:validation-api', '1.0.0.GA').scope :provided
jar('javax.validation:validation-api', '1.0.0.GA', 'sources').scope :provided

plugin( :compiler, '3.0' ).with( :source => '1.6', :target => '1.6' )

plugin('org.codehaus.mojo:gwt-maven-plugin', '${gwt.version}') do |gwt|
  gwt.with({ :warSourceDirectory => "${basedir}/public",
             :webXml => "${basedir}/public/WEB-INF/web.xml",
             :webappDirectory => "${basedir}/public",
             :hostedWebapp => "${basedir}/public",
             :inplace => true,
             :logLevel => "INFO",
             :treeLogger => true,
             :extraJvmArgs => "-Xmx512m",
             :gen => "${project.build.directory}/generated",
             :runTarget => "Users.html",
             :module => 'de.mkristian.ixtlan.users.UsersDevelopment',
             :style => "DETAILED",
             :includes => "**/de.mkristian.ixtlan.users.Users_NAMEGWTTestSuite.java",
             :draftCompile => true 
           })  
  gwt.in_phase( :'integration-test' ).execute_goal( 'test' )
  gwt.in_phase( :compile ).execute_goal( 'compile' )
  gwt.in_phase( :clean ).execute_goal( 'clean' )
end

profile :development do |p|
  # will go in future
end

profile :production do |p|
  p.plugin('org.codehaus.mojo:gwt-maven-plugin', '${gwt.version}') do |gwt|
    gwt.with({ :module => 'de.mkristian.ixtlan.users.Users',
               :style => "OBF",
               :draftCompile => false,
               :disableClassMetadata => true,
               :disableCastChecking => true,
               :optimizationLevel => 9})
  end
end

#-- Macs need the -d32 -XstartOnFirstThread jvm options -->
profile("mac") do |mac|
  mac.activation.os.family "mac"
  mac.plugin('org.codehaus.mojo:gwt-maven-plugin', '${gwt.version}').with(:extraJvmArgs => "-d32 -XstartOnFirstThread -Xmx512m")
end

# lock down versions
properties['gwt.version'] = '2.5.1-rc1'
properties['jruby.version'] = '1.7.4'
properties['jruby.plugins.version'] = '0.29.4'
# jetty version to run it in standalone jetty via jetty-run
properties['jetty.version'] = '8.1.9.v20130131'

# vim: syntax=Ruby
