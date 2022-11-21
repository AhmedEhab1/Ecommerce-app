package com.macaria.app.ui.homeScreen.profile.accountInfo.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.UserModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class AccountInfoViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<BaseModel<UserModel>> Response = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel> updatePasswordResponse = new MutableLiveData<>();


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

    public void changeAccountInfoRequest(CreateAccountRequest request, MultipartBody.Part image) {
        repository.changeAccountInfoRequest(request, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Response.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }

    public MutableLiveData<BaseModel<UserModel>> getResponse() {
        return Response;
    }

    public MutableLiveData<BaseModel> getUpdatePasswordResponse() {
        return updatePasswordResponse;
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

    public void updatePassword(ChangePasswordRequest request) {
        repository.updatePassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel model) {
                        updatePasswordResponse.setValue(model);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getErrorMessage(isHttpException(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
