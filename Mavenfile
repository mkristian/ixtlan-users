#-*- mode: ruby -*-
GWT_VERSION = '2.2.0'
jar('org.fusesource.restygwt:restygwt', '1.2-SNAPSHOT').scope :provided
jar('javax.ws.rs:jsr311-api', '1.1').scope :provided
jar('com.google.gwt:gwt-user', GWT_VERSION).scope :provided
jar('com.google.gwt.inject:gin', '1.5.0').scope :provided
jar('de.mkristian.rails-gwt:rails-gwt', '0.2.0-SNAPSHOT').scope :provided

plugin('org.codehaus.mojo:gwt-maven-plugin', GWT_VERSION) do |gwt|
  gwt.with({ :warSourceDirectory => "${basedir}/public",
             :webXml => "${basedir}/public/WEB-INF/web.xml",
             :webappDirectory => "${basedir}/public",
             :hostedWebapp => "${basedir}/public",
             :inplace => true,
             :logLevel => "INFO",
             :style => "DETAILED",
             :treeLogger => true,
             :extraJvmArgs => "-Xmx512m",
             :runTarget => "Users.html",
             :includes => "**/UsersGWTTestSuite.java"
           })
  gwt.executions.goals << ["clean", "compile", "test"]
end
plugin(:rails3).in_phase("initialize").execute_goal(:pom).with :force => true

#-- Macs need the -d32 -XstartOnFirstThread jvm options -->
profile("mac") do |mac|
  mac.activation.os.family "mac"
  mac.plugin('org.codehaus.mojo:gwt-maven-plugin').with(:extraJvmArgs => "-d32 -XstartOnFirstThread -Xmx512m")
end

repository(:snapshots).url "http://mojo.saumya.de"
# vim: syntax=Ruby
