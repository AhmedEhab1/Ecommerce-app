package com.macaria.app.ui.homeScreen.profile.contactUs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
