package com.macaria.app.ui.homeScreen.cart.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.data.FavoriteData;
import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.StoreOrderRequest;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderSummeryViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<CartModel>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public OrderSummeryViewModel(HomeRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<CartModel>> getModelMutableLiveData() {
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
        errorMassage = new MutableLiveData<>();
        modelMutableLiveData = new MutableLiveData<>();
    }

    public void storeOrder(StoreOrderRequest request) {
        repository.storeOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<CartModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<CartModel> model) {
                        modelMutableLiveData.setValue(model);
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



}
