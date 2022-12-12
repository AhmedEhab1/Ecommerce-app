package com.macaria.app.repository;

import com.macaria.app.models.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HomeRepository {
    private ApiService apiService ;

    @Inject
    public HomeRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseModel<ProductModel>> getProductsDetails(int id){
        return apiService.getProductDetails(id);
    }

    public Observable<BaseModel<List<ProductModel>>> getFavorite(){
        return apiService.getFavorite();
    }

    public Observable<BaseModel<List<CategoriesModel>>> getCategories(){
        return apiService.getCategories();
    }

    public Observable<BaseModel<HomeModel>> getHome(int id){
        return apiService.getHome(id);
    }

    public Observable<BaseModel> setFavorite(SetFavoriteRequest request){
        return apiService.setFavorite(request);
    }
}
