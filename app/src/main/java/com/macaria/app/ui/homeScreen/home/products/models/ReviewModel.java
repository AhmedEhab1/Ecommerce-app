package com.macaria.app.ui.homeScreen.home.products.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
