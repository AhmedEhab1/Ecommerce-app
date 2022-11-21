package com.macaria.app.ui.homeScreen.profile.accountInfo.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.UserModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountInfoViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<BaseModel<UserModel>> Response = new MutableLiveData<>();
    public MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public AccountInfoViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public void changeAccountInfoRequest(CreateAccountRequest request) {
        repository.changeAccountInfoRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Response.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }

    public MutableLiveData<BaseModel<UserModel>> getResponse() {
        return Response;
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
        Response = new MutableLiveData<>(); ;
    }

}
