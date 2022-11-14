package com.macaria.app.network;


import com.macaria.app.BaseModel;
import com.macaria.app.ui.login.model.LoginModel;
import com.macaria.app.ui.login.model.LoginRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Observable<BaseModel<LoginModel>> login(@Body LoginRequest request);
}
