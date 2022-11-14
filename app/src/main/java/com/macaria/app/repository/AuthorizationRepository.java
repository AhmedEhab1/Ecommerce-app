package com.macaria.app.repository;

import androidx.lifecycle.MutableLiveData;

import com.macaria.app.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.login.model.LoginModel;
import com.macaria.app.ui.login.model.LoginRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AuthorizationRepository {
    private ApiService apiService ;



    @Inject
    public AuthorizationRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseModel<LoginModel>> loginRequest(LoginRequest request){
        return apiService.login(request);
    }

}
