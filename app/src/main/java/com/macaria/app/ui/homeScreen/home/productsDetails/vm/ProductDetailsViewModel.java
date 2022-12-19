package com.macaria.app.ui.homeScreen.home.productsDetails.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailsViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<ProductModel>> modelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel> setFavorite = new MutableLiveData<>();
    private MutableLiveData<BaseModel<CartProductsModel>> addToCart = new MutableLiveData<>();


    @ViewModelInject
    public ProductDetailsViewModel(HomeRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<ProductModel>> getModelMutableLiveData() {
        return modelMutableLiveData;
    }

    public MutableLiveData<BaseModel<CartProductsModel>> getAddToCart() {
        return addToCart;
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

    public void clear() {
        errorMassage = new MutableLiveData<>();
        modelMutableLiveData = new MutableLiveData<>();
    }

    public void getProductsDetails(int id) {
        repository.getProductsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<ProductModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<ProductModel> listBaseModel) {
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

    public int add(int num) {
        int sum = num ;
        sum = num+1;
        return sum;
    }

    public int sub(int num){
        if (num != 1){
            num = num-1;
        }
        return num;
    }


    public void addToCart(AddToCartRequest request) {
        addToCart = new MutableLiveData<>();
        repository.addToCart(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<CartProductsModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<CartProductsModel> model) {
                        addToCart.setValue(model);
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
