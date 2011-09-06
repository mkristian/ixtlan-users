class UserMailer < ActionMailer::Base

  # default :from => ... does not work with dynamic values

  def send_password(user, password)
    @user = user
    @password = password

    mail :to => user.email, :subject => "reset access", :from => Configuration.instance.password_from_email
  end

  def send_new_account(user, password)
    @user = user
    @password = password
    @url = Configuration.instance.login_url

    mail :to => user.email, :subject => "access", :from => Configuration.instance.password_from_email
  end
end
