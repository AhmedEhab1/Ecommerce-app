package com.macaria.app.ui.homeScreen.profile.webViews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebViewModel {
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;

    public String getTerms() {
        return terms;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }
}
