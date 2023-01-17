package com.macaria.app.ui.homeScreen.categories.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.data.HomeData;
import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.ui.homeScreen.categories.models.PagesRequest;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.products.models.ColorModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<List<ProductModel>>> productsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel> setFavorite = new MutableLiveData<>();

    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public FilterViewModel(HomeRepository repository) {
        this.repository = repository;
    }


    public MutableLiveData<BaseModel<List<ProductModel>>> getProductsMutableLiveData() {
        return productsMutableLiveData;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    public MutableLiveData<BaseModel> getSetFavorite() {
        return setFavorite;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear() {
        errorMassage = new MutableLiveData<>();
        productsMutableLiveData = new MutableLiveData<>();
    }

    public void getPages(Map<String,  Object>  request) {
        repository.getPages(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> productsMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
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
                        errorMassage.setValue(isHttpException(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getColors() {
        repository.getColors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<ColorModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<ColorModel>> listBaseModel) {
                        HomeData.getInstance().setColorModels(listBaseModel.getItem().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("crash", "onError: ", e);
                    //    errorMassage.setValue(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getSizes() {
        repository.getSizes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<SizeModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<SizeModel>> listBaseModel) {
                        HomeData.getInstance().setSizeModels(listBaseModel.getItem().getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("crash", "onError: ", e);
                    //    errorMassage.setValue(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
