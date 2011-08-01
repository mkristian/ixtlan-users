require 'test_helper'

class UserTest < ActionMailer::TestCase
  test "send_password" do
    mail = User.send_password
    assert_equal "Send password", mail.subject
    assert_equal ["to@example.org"], mail.to
    assert_equal ["from@example.com"], mail.from
    assert_match "Hi", mail.body.encoded
  end

end
