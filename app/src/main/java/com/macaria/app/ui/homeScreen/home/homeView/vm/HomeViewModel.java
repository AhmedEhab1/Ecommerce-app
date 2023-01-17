package com.macaria.app.ui.homeScreen.home.homeView.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.data.HomeData;
import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.HomeRepository;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<HomeModel>> homeModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<CategoriesModel>>> categoryMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();
    private MutableLiveData<BaseModel> setFavorite = new MutableLiveData<>();

    @ViewModelInject
    public HomeViewModel(HomeRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<BaseModel<HomeModel>> getHomeModelMutableLiveData() {
        return homeModelMutableLiveData;
    }

    public MutableLiveData<BaseModel<List<CategoriesModel>>> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
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
        categoryMutableLiveData = new MutableLiveData<>();
        homeModelMutableLiveData = new MutableLiveData<>();
    }

    public void getHome(int category_id) {
        repository.getHome(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<HomeModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<HomeModel> homeModelBaseModel) {
                        setHomeData(homeModelBaseModel.getItem().getData());
                        homeModelMutableLiveData.setValue(homeModelBaseModel);
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

    public void getCategories() {
        repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<List<CategoriesModel>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel<List<CategoriesModel>> listBaseModel) {
                        categoryMutableLiveData.setValue(listBaseModel);
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

    public void setHomeData(HomeModel model){
        HomeData.getInstance().setHomeModel(model);
    }

    public void setCategoryList(List<CategoriesModel> categoriesModels){
        HomeData.getInstance().setCategoriesModel(categoriesModels);
    }

    public HomeModel getHomeData() {
        return HomeData.getInstance().getHomeModel();
    }

    public List<CategoriesModel> getCategoriesList(){
        return HomeData.getInstance().getCategoriesModel();
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

}
