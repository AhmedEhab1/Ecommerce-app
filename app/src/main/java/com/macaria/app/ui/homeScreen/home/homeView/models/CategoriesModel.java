package com.macaria.app.ui.homeScreen.home.homeView.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("items_count")
    @Expose
    private Integer items_count;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getItems_count() {
        return items_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
