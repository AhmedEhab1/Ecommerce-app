package com.macaria.app.ui.authorization.createAccount.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.AuthorizationRepository;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateAccountViewModel extends ViewModel {
    private AuthorizationRepository repository;
    private MutableLiveData<BaseModel<AuthModel>> loginResponse = new MutableLiveData<>();
    private MutableLiveData<BaseModel> forgetPasswordResponse = new MutableLiveData<>();

    public MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public CreateAccountViewModel(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void loginRequest(CreateAccountRequest request) {
        repository.createAccount(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> loginResponse.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

    public void verifyRequest(LoginRequest request) {
        repository.verifyRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> loginResponse.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }

    public void sendCode(LoginRequest request) {
        repository.forgetPasswordRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> forgetPasswordResponse.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        }
                );
    }


    public MutableLiveData<BaseModel<AuthModel>> getLoginResponse() {
        return loginResponse;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    public MutableLiveData<BaseModel> sendCodeResponse() {
        return forgetPasswordResponse;
    }


    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear() {
        errorMassage = new MutableLiveData<>();
        ;
        loginResponse = new MutableLiveData<>();
        ;
    }
}
