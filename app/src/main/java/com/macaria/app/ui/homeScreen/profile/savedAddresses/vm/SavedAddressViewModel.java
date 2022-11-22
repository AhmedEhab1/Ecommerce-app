package com.macaria.app.ui.homeScreen.profile.savedAddresses.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.model.AddressModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class SavedAddressViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<AddressModel>>> addressModel = new MutableLiveData<>();
    private MutableLiveData<BaseModel> deleteAddress = new MutableLiveData<>();


    @ViewModelInject
    public SavedAddressViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public void deleteAddress(int request) {
        repository.deleteAddress(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> deleteAddress.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }



    public MutableLiveData<BaseModel<List<AddressModel>>> getAddressModel() {
        return addressModel;
    }
    public MutableLiveData<BaseModel> getDeleteAddressResponse() {
        return deleteAddress;
    }


    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear() {
        errorMassage = new MutableLiveData<>();
        addressModel = new MutableLiveData<>();
    }

    public void getAddress() {
        repository.getAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<AddressModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<AddressModel>> model) {
                        addressModel.setValue(model);
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
