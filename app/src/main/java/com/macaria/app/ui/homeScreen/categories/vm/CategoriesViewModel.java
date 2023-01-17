package com.macaria.app.ui.homeScreen.categories.vm;

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

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesViewModel extends ViewModel {
    private HomeRepository repository;
    private MutableLiveData<BaseModel<List<CategoriesModel>>> categoryMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<CategoriesModel>>> SubCategoryMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<BaseModel<List<CategoriesModel>>> SubItemMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();

    @ViewModelInject
    public CategoriesViewModel(HomeRepository repository) {
        this.repository = repository;
    }


    public MutableLiveData<BaseModel<List<CategoriesModel>>> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
    }

    public MutableLiveData<String> getErrorMassage() {
        return errorMassage;
    }

    public MutableLiveData<BaseModel<List<CategoriesModel>>> getSubCategoryMutableLiveData() {
        return SubCategoryMutableLiveData;
    }

    public MutableLiveData<BaseModel<List<CategoriesModel>>> getSubItemMutableLiveData() {
        return SubItemMutableLiveData;
    }

    // show error alert dialog
    private void getErrorMessage(String message) {
        errorMassage.setValue(message);
    }

    public void clear() {
        errorMassage = new MutableLiveData<>();
        categoryMutableLiveData = new MutableLiveData<>();
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

    public void setHomeData(HomeModel model) {
        HomeData.getInstance().setHomeModel(model);
    }

    public void setCategoryList(List<CategoriesModel> categoriesModels) {
        HomeData.getInstance().setCategoriesModel(categoriesModels);
    }

    public List<CategoriesModel> getCategoriesList() {
        return HomeData.getInstance().getCategoriesModel();
    }

    public void getSubCategory(int category_id) {
        repository.getSubCategories(category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> SubCategoryMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }

    public void getSubItem(int sub_category_id) {
        repository.getSubItem(sub_category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> SubItemMutableLiveData.setValue(response),
                        error -> {
                            Log.d("viewModel error", "getErrorMessage: " + error);
                            getErrorMessage(isHttpException(error));
                        });
    }
}
