package com.macaria.app.repository;

import com.macaria.app.models.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AuthorizationRepository {
    private ApiService apiService ;

    @Inject
    public AuthorizationRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseModel<AuthModel>> loginRequest(LoginRequest request){
        return apiService.login(request);
    }

    public Observable<BaseModel<AuthModel>> createAccount(CreateAccountRequest request){
        return apiService.createAccount(request);
    }

    public Observable<BaseModel<ForgetPasswordModel>> forgetPasswordRequest(LoginRequest request){
        return apiService.forgetPassword(request);
    }

    public Observable<BaseModel<ForgetPasswordModel>> confirmCode(ForgetPasswordModel request){
        return apiService.confirmCode(request);
    }

    public Observable<BaseModel<AuthModel>> verifyRequest(LoginRequest request){
        return apiService.verifyAccount(request);
    }

    public Observable<BaseModel> updatePassword(ChangePasswordRequest request){
        return apiService.updatePassword(request);
    }

    public Observable<BaseModel> logout(){
        return apiService.logout();
    }


}
