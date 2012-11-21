rest = Users::Application.config.rest

rest.server( :gettext ) do |server|
  server.url = "http://localhost:3000"
  server.options[:headers] = {'X-Service-Token' => 'behappy'}
  server.add_model( Locale, :locales ) 
end
