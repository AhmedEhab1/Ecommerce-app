package com.macaria.app.ui.homeScreen.favorite.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.data.FavoriteData;
import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<List<ProductModel>>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel> setFavorite = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public FavoriteViewModel(HomeRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<List<ProductModel>>> getModelMutableLiveData() {
        return modelMutableLiveData;
    }

    public MutableLiveData<BaseModel> getSetFavorite() {
        return setFavorite;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear(){
        errorMassage = new MutableLiveData<>();
        setFavorite = new MutableLiveData<>();
        modelMutableLiveData = new MutableLiveData<>();
    }

    public void getFavorite() {
        clear();
        repository.getFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<ProductModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<ProductModel>> listBaseModel) {
                        modelMutableLiveData.setValue(listBaseModel);
                        saveFavoriteData(listBaseModel.getItem().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("crash", "onError: ", e);
                        errorMassage.setValue(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setFavorite(SetFavoriteRequest request) {
        repository.setFavorite(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel model) {
                        setFavorite.setValue(model);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("crash", "onError: ", e);
                        errorMassage.setValue(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void saveFavoriteData(List<ProductModel> models){
        FavoriteData.getInstance().setFavoriteData(models);
    }

}
