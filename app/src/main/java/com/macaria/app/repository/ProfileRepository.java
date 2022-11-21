package com.macaria.app.repository;

import com.macaria.app.models.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ProfileRepository {
    private ApiService apiService ;

    @Inject
    public ProfileRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseModel<UserModel>> changeAccountInfoRequest(CreateAccountRequest request){
        return apiService.changeAccountInfo(request);
    }

}
