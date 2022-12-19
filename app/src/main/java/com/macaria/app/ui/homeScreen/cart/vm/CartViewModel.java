package com.macaria.app.ui.homeScreen.cart.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.data.FavoriteData;
import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<CartModel>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel<CartProductsModel>> addOrSubMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel> deleteItemMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel> promoCodeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public CartViewModel(HomeRepository repository) {
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

    public MutableLiveData<BaseModel> getDeleteItemMutableLiveData() {
        return deleteItemMutableLiveData;
    }

    public MutableLiveData<BaseModel> getPromoCodeMutableLiveData() {
        return promoCodeMutableLiveData;
    }

    public void clear(){
        errorMassage = new MutableLiveData<>();
        modelMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<BaseModel<CartProductsModel>> getAddOrSubMutableLiveData() {
        return addOrSubMutableLiveData;
    }

    public void getCart() {
        repository.getCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<CartModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<CartModel> listBaseModel) {
                        modelMutableLiveData.setValue(listBaseModel);
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

    public void addItem(AddToCartRequest request) {
        repository.addCartItem(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> addOrSubMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

    public void subItem(AddToCartRequest request) {
        repository.subCartItem(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> addOrSubMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

    public void deleteItem(int request) {
        repository.deleteCartItem(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> deleteItemMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

    public void promoCode(int request) {
        repository.promoCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> promoCodeMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

}
