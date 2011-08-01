class UserMailer < ActionMailer::Base
  default :from => Configuration.instance.password_from_email

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.user_mailer.send_password.subject
  #
  def send_password(user, password)
    @user = user
    @password = password

    mail :to => user.email
  end

  def send_new_account(user, password)
    @user = user
    @password = password
    @url = Configuration.instance.login_url

    mail :to => user.email
  end
end
