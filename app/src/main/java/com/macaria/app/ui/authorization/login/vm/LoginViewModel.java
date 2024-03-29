package com.macaria.app.ui.authorization.login.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.AuthorizationRepository;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private AuthorizationRepository repository;
    private MutableLiveData<BaseModel<AuthModel>> loginResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public LoginViewModel(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void loginRequest(LoginRequest request) {
        repository.loginRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> loginResponse.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }


    public MutableLiveData<BaseModel<AuthModel>> getLoginResponse() {
        return loginResponse;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear(){
        errorMassage = new MutableLiveData<>(); ;
        loginResponse = new MutableLiveData<>(); ;
    }

}
