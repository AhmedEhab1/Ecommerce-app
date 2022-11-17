package com.macaria.app.ui.authorization.forgetPassword.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.AuthorizationRepository;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgetPasswordViewModel extends ViewModel {
    private AuthorizationRepository repository;
    private MutableLiveData<BaseModel<ForgetPasswordModel>> forgetPasswordResponse = new MutableLiveData<>();
    private MutableLiveData<BaseModel<ForgetPasswordModel>> confirmCode = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public ForgetPasswordViewModel(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void forgetPasswordRequest(LoginRequest request) {
        repository.forgetPasswordRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<ForgetPasswordModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<ForgetPasswordModel> model) {
                        forgetPasswordResponse.setValue(model);

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

    public void confirmCode(ForgetPasswordModel request) {
        repository.confirmCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<ForgetPasswordModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<ForgetPasswordModel> model) {
                        confirmCode.setValue(model);

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

    public MutableLiveData<BaseModel<ForgetPasswordModel>> getResponse() {
        return forgetPasswordResponse;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

}
