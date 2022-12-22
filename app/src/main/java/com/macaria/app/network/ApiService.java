package com.macaria.app.network;


import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
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
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;
import com.macaria.app.ui.homeScreen.profile.faq.models.FaqModel;
import com.macaria.app.ui.homeScreen.profile.faq.vm.FaqViewModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.CitiesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.DeleteRequest;
import com.macaria.app.ui.homeScreen.profile.webViews.WebViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @POST("auth/login")
    Observable<BaseModel<AuthModel>> login(@Body LoginRequest request);

    @POST("auth/register")
    Observable<BaseModel<AuthModel>> createAccount(@Body CreateAccountRequest request);

    @POST("forgot-password/sendCode")
    Observable<BaseModel<ForgetPasswordModel>> forgetPassword(@Body LoginRequest request);

    @POST("auth/verify")
    Observable<BaseModel<AuthModel>> verifyAccount(@Body LoginRequest request);

    @POST("forgot-password/confirmCode")
    Observable<BaseModel<ForgetPasswordModel>> confirmCode(@Body ForgetPasswordModel request);

    @POST("forgot-password/updatePassword")
    Observable<BaseModel> updatePassword(@Body ChangePasswordRequest request);

    @POST("profile/changePassword")
    Observable<BaseModel> changePassword(@Body ChangePasswordRequest request);

    @POST("profile/address/store")
    Observable<BaseModel> addAddress(@Body AddAddressRequest request);

    @POST("profile/address/update")
    Observable<BaseModel> updateAddress(@Body AddAddressRequest request);

    @POST("contactUs")
    Observable<BaseModel> contactUs(@Body ContactUsRequest request);

    @GET("fav")
    Observable<BaseModel<List<ProductModel>>> getFavorite();

    @GET("cart")
    Observable<BaseModel<CartModel>> getCart();

    @GET("products/pages")
    Observable<BaseModel<List<ProductModel>>> getPages(@QueryMap Map<String, Integer> params);

    @GET("categories")
    Observable<BaseModel<List<CategoriesModel>>> getCategories();

    @GET("categories/sub")
    Observable<BaseModel<List<CategoriesModel>>> getSubCategories(@Query("category_id") int category_id);

    @GET("categories/subItems")
    Observable<BaseModel<List<CategoriesModel>>> getSubItem(@Query("sub_category_id") int sub_category_id);

    @GET("home")
    Observable<BaseModel<HomeModel>> getHome(@Query("category_id")int id);

    @POST("reviews/store")
    Observable<BaseModel> addReview(@Body AddReviewRequest request);

    @POST("fav/store")
    Observable<BaseModel> setFavorite(@Body SetFavoriteRequest request);

    @POST("cart/store")
    Observable<BaseModel<CartProductsModel>> addToCart(@Body AddToCartRequest request);

    @POST("cart/add")
    Observable<BaseModel<CartProductsModel>> addCartItem(@Body AddToCartRequest request);

    @POST("cart/sub")
    Observable<BaseModel<CartProductsModel>> subCartItem(@Body AddToCartRequest request);

    @POST("cart/delete")
    Observable<BaseModel> deleteCartItem(@Body DeleteRequest id);

    @POST("orders/promoCode")
    Observable<BaseModel> promoCode(@Body PromoCodeRequest id);

    @GET("payment")
    Observable<BaseModel<PaymentTokenModel>> getPaymentToken(@Query("type") String type);

    @POST("orders/store")
    Observable<BaseModel<CartModel>> storeOrder(@Body StoreOrderRequest request);

    @POST("profile/update")
    Observable<BaseModel<UserModel>> changeAccountInfo(@Body CreateAccountRequest request);

    @GET("profile/address")
    Observable<BaseModel<List<AddressModel>>> getAddress();

    @GET("profile/orders")
    Observable<BaseModel<List<OrderHistoryModel>>> getOrderHistory();

    @GET("contact")
    Observable<BaseModel<ContactModel>> requestContact();

    @GET("list/cities")
    Observable<BaseModel<List<CitiesModel>>> getCities();

    @GET("faqs")
    Observable<BaseModel<List<FaqModel>>> getFaq();

    @POST("profile/address/delete")
    Observable<BaseModel> deleteAddress(@Body DeleteRequest id);

    @POST("logout")
    Observable<BaseModel> logout();

    @GET("webViews/links")
    Observable<BaseModel<WebViewModel>> getWebViewsLinks();

    @Multipart
    @POST("profile/update")
    Observable<BaseModel<UserModel>> changeAccountInfo(@PartMap HashMap<String, String> map, @Part MultipartBody.Part Image);

    @GET("product/details")
    Observable<BaseModel<ProductModel>> getProductDetails(@Query("id") int id);
}
