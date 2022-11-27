package com.macaria.app.ui.homeScreen.profile.orderHistory.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderHistoryViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<BaseModel<List<OrderHistoryModel>>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();


    @ViewModelInject
    public OrderHistoryViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<List<OrderHistoryModel>>> getModelMutableLiveData() {
        return modelMutableLiveData;
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
        modelMutableLiveData = new MutableLiveData<>(); ;
    }

    public void getOrderHistory() {
        repository.getOrderHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<OrderHistoryModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<OrderHistoryModel>> listBaseModel) {
                        modelMutableLiveData.setValue(listBaseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
