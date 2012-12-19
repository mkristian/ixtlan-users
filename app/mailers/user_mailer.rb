class UserMailer

  def self.send_password(user)
    Pony.mail :to => user.email, :subject => "", :from => ::Configuration.instance.from_email, :body => user.password
  end

  def self.send_reset_password(user)
    Pony.mail :to => user.email, :subject => "reset access", :from => ::Configuration.instance.from_email, :body => reset_password_body( user )
  end

  def self.send_new_user(user)
    Pony.mail :to => user.email, :subject => "access", :from => ::Configuration.instance.from_email, :body => new_user_body( user ) 
  end

  private

  def self.reset_password_body( user )
    text = 'hello' + " #{user.name},\n\n"
    text += 'new password' + ": #{user.password}\n\n"
    text += 'in case you did not ask for a new password just ignore this email.' + "\n\n"
    text += 'enjoy !'
    text
  end

  def self.new_user_body( user )
    text = 'hello' + " #{user.name},\n\n"
    text += 'profile url' + ": #{::Configuration.instance.profile_url}\n"
    urls( user ).each do |app, url|
      text += "#{app} url: #{url}\n"
    end
    text += 'username' + ": #{user.login}\n"
    text += 'password' + ': ' + 'will be sent in a separate email' + "\n\n"
    text += 'enjoy !'
    text
  end

  def self.urls(user)
    Hash[
         user.applications.collect do |app|
           case app
           when Application.ALL
             ['ATs application', Configuration.instance.ats_url]
           when Application.THIS
             ['users admin', app.url]
           else
             [app.name, app.url]
           end
         end
        ]
  end
end
