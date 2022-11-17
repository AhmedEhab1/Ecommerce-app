package com.macaria.app.ui.authorization.forgetPassword.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPasswordModel {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("code")
    @Expose
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
