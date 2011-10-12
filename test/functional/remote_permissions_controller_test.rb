require 'test_helper'

class RemotePermissionsControllerTest < ActionController::TestCase
  setup do
    @remote_permission = remote_permissions(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:remote_permissions)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create remote_permission" do
    assert_difference('RemotePermission.count') do
      post :create, :remote_permission => @remote_permission.attributes
    end

    assert_redirected_to remote_permission_path(assigns(:remote_permission))
  end

  test "should show remote_permission" do
    get :show, :id => @remote_permission.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @remote_permission.to_param
    assert_response :success
  end

  test "should update remote_permission" do
    put :update, :id => @remote_permission.to_param, :remote_permission => @remote_permission.attributes
    assert_redirected_to remote_permission_path(assigns(:remote_permission))
  end

  test "should destroy remote_permission" do
    assert_difference('RemotePermission.count', -1) do
      delete :destroy, :id => @remote_permission.to_param
    end

    assert_redirected_to remote_permissions_path
  end
end
