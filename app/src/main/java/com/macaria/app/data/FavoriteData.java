package com.macaria.app.data;

import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.List;

public class FavoriteData {
    private static FavoriteData instance;

    static {
        instance = new FavoriteData();
    }

    private FavoriteData() {
    }

    private List<ProductModel> favoriteData;

    public static FavoriteData getInstance() {
        return instance;
    }

    public List<ProductModel> getFavoriteData() {
        return favoriteData;
    }

    public void setFavoriteData(List<ProductModel> favoriteData) {
        this.favoriteData = favoriteData;
    }
}
