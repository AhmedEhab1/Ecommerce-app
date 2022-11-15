package com.macaria.app.ui.login.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.BaseModel;
import com.macaria.app.repository.AuthorizationRepository;
import com.macaria.app.ui.login.model.LoginModel;
import com.macaria.app.ui.login.model.LoginRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private AuthorizationRepository repository ;
    private MutableLiveData<BaseModel<LoginModel>> loginResponse = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public LoginViewModel(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void loginRequest(LoginRequest request) {
        repository.loginRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> loginResponse.setValue(response),
                        error -> getErrorMessage(error.getMessage()));
    }


    public MutableLiveData<BaseModel<LoginModel>> getLoginResponse() {
        return loginResponse;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message){
        errorMassage.setValue(message);
        Log.e("viewModel", message);
    }

}
