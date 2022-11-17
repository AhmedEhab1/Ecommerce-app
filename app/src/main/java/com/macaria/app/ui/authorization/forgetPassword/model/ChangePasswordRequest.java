package com.macaria.app.ui.authorization.forgetPassword.model;

public class ChangePasswordRequest {
    int user_id ;
    String password ;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
