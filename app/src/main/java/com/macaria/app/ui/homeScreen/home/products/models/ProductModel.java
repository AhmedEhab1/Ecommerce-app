package com.macaria.app.ui.homeScreen.home.products.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.macaria.app.models.BaseModel;

import java.util.List;

public class ProductModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("review_count")
    @Expose
    private Integer reviewCount;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("old_price")
    @Expose
    private String oldPrice;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("size_chart")
    @Expose
    private String sizeChart;
    @SerializedName("is_fav")
    @Expose
    private Boolean isFav;
    @SerializedName("colors")
    @Expose
    private List<ColorModel> colors = null;
    @SerializedName("sizes")
    @Expose
    private List<SizeModel> sizes = null;
    @SerializedName("reviews")
    @Expose
    private List<ReviewModel> reviews = null;
    @SerializedName("suggestedProducts")
    @Expose
    private BaseModel.Item<List<SuggestedProducts>> suggestedProducts;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRate() {
        return rate;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public String getPrice() {
        return price;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public String getPercentage() {
        return percentage;
    }

    public String getImage() {
        return image;
    }

    public List<String> getImages() {
        return images;
    }

    public String getSizeChart() {
        return sizeChart;
    }

    public Boolean getFav() {
        return isFav;
    }

    public List<ColorModel> getColors() {
        return colors;
    }

    public List<SizeModel> getSizes() {
        return sizes;
    }

    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public BaseModel.Item<List<SuggestedProducts>> getSuggestedProducts() {
        return suggestedProducts;
    }
}
