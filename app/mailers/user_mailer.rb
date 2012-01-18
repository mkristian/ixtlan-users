class UserMailer < ActionMailer::Base

  # default :from => ... does not work with dynamic values

  def send_password(user)
    @user = user
    @password = user.password

    mail :to => user.email, :subject => "", :from => Configuration.instance.from_email
  end

  def send_reset_password(user)
    @user = user
    @password = user.password

    mail :to => user.email, :subject => "reset access", :from => Configuration.instance.from_email
  end

  def send_new_user(user)
    @user = user
    @profile_url = Configuration.instance.profile_url
    @urls = urls(user)

    mail :to => user.email, :subject => "access", :from => Configuration.instance.from_email
  end

  private

  def urls(user)
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
