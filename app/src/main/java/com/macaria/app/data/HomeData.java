package com.macaria.app.data;

import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;

import java.util.List;

public class HomeData {
    private static HomeData instance;
    static {
        instance = new HomeData();
    }

    private HomeData() {
    }

    private HomeModel homeModel ;
    private List<CategoriesModel> categoriesModel ;

    public static HomeData getInstance() {
        return instance;
    }

    public HomeModel getHomeModel() {
        return homeModel;
    }

    public void setHomeModel(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    public List<CategoriesModel> getCategoriesModel() {
        return categoriesModel;
    }

    public void setCategoriesModel(List<CategoriesModel> categoriesModel) {
        this.categoriesModel = categoriesModel;
    }
}
