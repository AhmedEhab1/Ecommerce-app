package com.macaria.app.ui.homeScreen.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentTokenModel {
    @SerializedName("key")
    @Expose
    private Key key;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public static class Key {

        @SerializedName("token")
        @Expose
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}
