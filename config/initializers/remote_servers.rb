rest = Users::Application.config.rest

rest.server( :translations ) do |server|
  config = Ixtlan::Passwords.get( :rest ).get( :translations )
  server.url = config.get( :url, "http://localhost:3000" )
  server.options[ :headers ] = {'X-Service-Token' => config.get( :token, 'behappy' )}
  server.add_model( Locale, :locales )
end
