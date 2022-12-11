package com.macaria.app.ui.homeScreen.home.homeView.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SuggestedProducts;

import java.util.List;

public class HomeModel {
    @SerializedName("new_arrivels")
    @Expose
    private BaseModel.Item<List<ProductModel>>  newArrivals;
    @SerializedName("best_sellers")
    @Expose
    private BaseModel.Item<List<ProductModel>>  bestSellers;
    @SerializedName("first_ads")
    @Expose
    private BaseModel.Item<BannerModel> firstAds;
    @SerializedName("second_ads")
    @Expose
    private BaseModel.Item<BannerModel> secondAds;
    @SerializedName("third_ads")
    @Expose
    private BaseModel.Item<BannerModel> thirdAds;
    @SerializedName("footer_ads")
    @Expose
    private BaseModel.Item<List<BannerModel>> footerAds;


    public BaseModel.Item<List<ProductModel>> getNewArrivals() {
        return newArrivals;
    }

    public BaseModel.Item<List<ProductModel>> getBestSellers() {
        return bestSellers;
    }

    public BaseModel.Item<BannerModel> getFirstAds() {
        return firstAds;
    }

    public BaseModel.Item<BannerModel> getSecondAds() {
        return secondAds;
    }

    public BaseModel.Item<BannerModel> getThirdAds() {
        return thirdAds;
    }

    public BaseModel.Item<List<BannerModel>> getFooterAds() {
        return footerAds;
    }
}
