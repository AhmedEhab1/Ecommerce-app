package com.macaria.app.network;


import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.CitiesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.DeleteRequest;
import com.macaria.app.ui.homeScreen.profile.webViews.WebViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    @POST("reviews/store")
    Observable<BaseModel> addReview(@Body AddReviewRequest request);

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

    @POST("profile/address/delete")
    Observable<BaseModel> deleteAddress(@Body DeleteRequest id);


    @POST("logout")
    Observable<BaseModel> logout();

    @GET("webViews/links")
    Observable<BaseModel<WebViewModel>> getWebViewsLinks();

    @Multipart
    @POST("profile/update")
    Observable<BaseModel<UserModel>> changeAccountInfo(@PartMap HashMap<String, String> map, @Part MultipartBody.Part Image);
}
