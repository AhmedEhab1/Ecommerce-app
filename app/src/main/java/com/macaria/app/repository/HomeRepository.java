package com.macaria.app.repository;

import com.macaria.app.models.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.cart.model.PaymentTokenModel;
import com.macaria.app.ui.homeScreen.cart.model.PromoCodeRequest;
import com.macaria.app.ui.homeScreen.cart.model.StoreOrderRequest;
import com.macaria.app.ui.homeScreen.categories.models.PagesRequest;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.products.models.ColorModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.DeleteRequest;

import java.util.List;
import java.util.Map;

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

    public Observable<BaseModel<CartModel>> getCart(){
        return apiService.getCart();
    }

    public Observable<BaseModel<List<ProductModel>>> getPages(Map<String, Object>  request){
        return apiService.getPages(request);
    }

    public Observable<BaseModel<List<CategoriesModel>>> getCategories(){
        return apiService.getCategories();
    }
    public Observable<BaseModel<List<ColorModel>>> getColors(){
        return apiService.getColors();
    }

    public Observable<BaseModel<List<SizeModel>>> getSizes(){
        return apiService.getSizes();
    }

    public Observable<BaseModel<List<CategoriesModel>>> getCategoriesTrend(){
        return apiService.getCategoriesTrend();
    }

    public Observable<BaseModel<List<CategoriesModel>>> getSubCategories(int category_id){
        return apiService.getSubCategories(category_id);
    }

    public Observable<BaseModel<List<CategoriesModel>>> getSubItem(int subItems){
        return apiService.getSubItem(subItems);
    }

    public Observable<BaseModel<HomeModel>> getHome(int id){
        return apiService.getHome(id);
    }

    public Observable<BaseModel> setFavorite(SetFavoriteRequest request){
        return apiService.setFavorite(request);
    }

    public Observable<BaseModel<CartProductsModel>> addToCart(AddToCartRequest request){
        return apiService.addToCart(request);
    }

    public Observable<BaseModel<CartModel>> addCartItem(AddToCartRequest request){
        return apiService.addCartItem(request);
    }

    public Observable<BaseModel<CartModel>> subCartItem(AddToCartRequest request){
        return apiService.subCartItem(request);
    }

    public Observable<BaseModel<CartModel>> deleteCartItem(int id){
        DeleteRequest request = new DeleteRequest();
        request.setId(id);
        return apiService.deleteCartItem(request);
    }

    public Observable<BaseModel<CartModel>> promoCode(String id){
        PromoCodeRequest request = new PromoCodeRequest();
        request.setPromo_code(id);
        return apiService.promoCode(request);
    }

    public Observable<BaseModel<CartModel>> storeOrder(StoreOrderRequest request){
        return apiService.storeOrder(request);
    }

    public Observable<BaseModel<PaymentTokenModel>> getPaymentToken(){
        return apiService.getPaymentToken("api");
    }
}
