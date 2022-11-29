package com.macaria.app.ui.homeScreen.profile.faq.vm;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.profile.faq.models.FaqModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FaqViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<BaseModel<List<FaqModel>>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();


    @ViewModelInject
    public FaqViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<List<FaqModel>>> getModelMutableLiveData() {
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

    public void getFaq() {
        repository.getFaq()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<FaqModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<FaqModel>> listBaseModel) {
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
