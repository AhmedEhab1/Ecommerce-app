package com.macaria.app.network;


import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
