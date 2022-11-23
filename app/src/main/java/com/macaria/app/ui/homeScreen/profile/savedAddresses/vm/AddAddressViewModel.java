package com.macaria.app.ui.homeScreen.profile.savedAddresses.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.CitiesModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddAddressViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<CitiesModel>>> citiesResponse = new MutableLiveData<>();
    private MutableLiveData<BaseModel> addAddress = new MutableLiveData<>();

    @ViewModelInject
    public AddAddressViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<List<CitiesModel>>> getCitiesResponse() {
        return citiesResponse;
    }
    public MutableLiveData<BaseModel> getAddAddressResponse() {
        return addAddress;
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
        citiesResponse = new MutableLiveData<>();
    }

    public void addAddress(AddAddressRequest request) {
        repository.addAddress(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull BaseModel model) {
                        addAddress.setValue(model);
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

    public void updateAddress(AddAddressRequest request) {
        repository.updateAddress(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel model) {
                        addAddress.setValue(model);
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

    public void getCities() {
        repository.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<CitiesModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<CitiesModel>> model) {
                        citiesResponse.setValue(model);
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
