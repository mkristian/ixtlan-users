#-*- mode: ruby -*-
GWT_VERSION = '2.4.0' unless defined? GWT_VERSION
jar('de.mkristian.gwt:rails-gwt', '0.8.0-SNAPSHOT').scope :provided
jar('org.fusesource.restygwt:restygwt', '1.3').scope :provided
jar('javax.ws.rs:jsr311-api', '1.1').scope :provided
jar('com.google.gwt:gwt-user', GWT_VERSION).scope :provided
jar('com.google.gwt.inject:gin', '1.5.0').scope :provided
jar('javax.validation:validation-api', '1.0.0.GA').scope :provided
jar('javax.validation:validation-api', '1.0.0.GA', 'sources').scope :provided

plugin('org.codehaus.mojo:gwt-maven-plugin', GWT_VERSION) do |gwt|
  gwt.with({ :warSourceDirectory => "${basedir}/public",
             :webXml => "${basedir}/public/WEB-INF/web.xml",
             :webappDirectory => "${basedir}/public",
             :hostedWebapp => "${basedir}/public",
             :inplace => true,
             :logLevel => "INFO",
             :treeLogger => true,
             :extraJvmArgs => "-Xmx512m",
             :gen => "${project.build.directory}/generated",
             :runTarget => "Users.html"
           })
  gwt.executions.goals << ["clean", "compile", "test"]
end
plugin(:rails3).in_phase("initialize").execute_goal(:pom).with :force => true
properties['jruby-plugins-version'] = '0.30.0'
#-- Macs need the -d32 -XstartOnFirstThread jvm options -->
profile("mac") do |mac|
  mac.activation.os.family "mac"
  mac.plugin('org.codehaus.mojo:gwt-maven-plugin').with(:extraJvmArgs => "-d32 -XstartOnFirstThread -Xmx512m")
end

# to get the restygwt and rails-gwt snapshots
#repository("snapshots") do |snapshots|
#  snapshots.url "http://http://mojo.saumya.de/"
#  snapshots.releases(:enabled => false)
#  snapshots.snapshots(:enabled => true)
#end

# latest version
jar 'org.jruby.rack:jruby-rack', '1.1.7'

plugin( :compiler, '3.0' ).with( :source => '1.6', :target => '1.6' )

profile :development do |p|
  p.activation.by_default
  p.plugin('org.codehaus.mojo:gwt-maven-plugin', GWT_VERSION) do |gwt|
    gwt.with({ :module => 'de.mkristian.ixtlan.users.UsersDevelopment',
               :style => "DETAILED",
               :includes => "**/UsersGWTTestSuite.java",
               :draftCompile => true })
  end
end

profile :production do |p|
  p.plugin('org.codehaus.mojo:gwt-maven-plugin', GWT_VERSION) do |gwt|
    gwt.with({ :module => 'de.mkristian.ixtlan.users.Users',
               :style => "OBF",
               :draftCompile => false,
               :disableClassMetadata => true,
               :disableCastChecking => true,
               :optimizationLevel => 9})
  end
end
# vim: syntax=Ruby
