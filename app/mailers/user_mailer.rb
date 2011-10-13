class UserMailer < ActionMailer::Base

  # default :from => ... does not work with dynamic values

  def send_password(user)
    @user = user
    @password = user.password

    mail :to => user.email, :subject => "reset access", :from => Configuration.instance.from_email
  end

  def send_new_user(user)
    @user = user
    @password = user.password
    @urls = user.application_urls

    mail :to => user.email, :subject => "access", :from => Configuration.instance.from_email
  end

  def send_new_urls(user)
    @user = user
    @urls = user.application_urls

    mail :to => user.email, :subject => "access urls", :from => Configuration.instance.from_email
  end
end
