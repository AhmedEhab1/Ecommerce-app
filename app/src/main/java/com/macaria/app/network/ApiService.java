package com.macaria.app.network;


import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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

    @POST("profile/update")
    Observable<BaseModel<UserModel>> changeAccountInfo(@Body CreateAccountRequest request);

    @Multipart
    @POST("profile/update")
    Observable<BaseModel<UserModel>> changeAccountInfo(@PartMap HashMap<String, String> map, @Part MultipartBody.Part Image);
}
