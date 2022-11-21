package com.macaria.app.ui.authorization.forgetPassword.model;

public class ChangePasswordRequest {
    int user_id ;
    String password , new_password;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
