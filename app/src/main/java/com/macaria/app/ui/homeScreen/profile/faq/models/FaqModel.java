package com.macaria.app.ui.homeScreen.profile.faq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_expand")
    @Expose
    private Boolean isExpand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(Boolean isExpand) {
        this.isExpand = isExpand;
    }

}
