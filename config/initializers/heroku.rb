if ENV['SENDGRID_USERNAME']
  Pony.options = {
    :via         => :smtp,
    :via_options => {
      :address        => 'smtp.sendgrid.net',
      :port           => '587',
      :authentication => :plain,
      :user_name      => ENV['SENDGRID_USERNAME'],
      :password       => ENV['SENDGRID_PASSWORD'],
      :domain         => 'heroku.com'
    }
  }
end
