package com.macaria.app.ui.homeScreen.profile.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.AuthorizationRepository;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.homeScreen.profile.webViews.WebViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {
    private AuthorizationRepository repository;
    private MutableLiveData<BaseModel> logoutResponse = new MutableLiveData<>();
    private MutableLiveData<BaseModel<WebViewModel>> webViewResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public ProfileViewModel(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void loginRequest() {
        repository.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> logoutResponse.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }

    public void webViewRequest() {
        repository.getWebViewsLinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> webViewResponse.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }

    public MutableLiveData<BaseModel> getLogoutResponse() {
        return logoutResponse;
    }

    public MutableLiveData<BaseModel<WebViewModel>> getWebViewResponse() {
        return webViewResponse;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }


}
