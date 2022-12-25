package com.macaria.app.ui.homeScreen.home.search.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<CategoriesModel>>> trendModel = new MutableLiveData<>();
    private MutableLiveData<BaseModel> deleteAddress = new MutableLiveData<>();


    @ViewModelInject
    public SearchViewModel(HomeRepository repository) {
        this.repository = repository;
    }

//    public void deleteAddress(int request) {
//        repository.deleteAddress(request)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> deleteAddress.setValue(response),
//                        error ->
//                                getErrorMessage(isHttpException(error)));
//    }


    public MutableLiveData<BaseModel<List<CategoriesModel>>> getTrendModel() {
        return trendModel;
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
        trendModel = new MutableLiveData<>();
    }

    public void getCategoriesTrend() {
        repository.getCategoriesTrend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<CategoriesModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<CategoriesModel>> model) {
                        trendModel.setValue(model);
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
