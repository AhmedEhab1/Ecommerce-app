package com.macaria.app.ui.homeScreen.home.homeView.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("redirct_id")
    @Expose
    private Integer redirctId;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRedirctId() {
        return redirctId;
    }

    public void setRedirctId(Integer redirctId) {
        this.redirctId = redirctId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
