package com.macaria.app.ui.homeScreen.profile.contactUs.vm;

import static com.macaria.app.utilities.JsonHelper.isHttpException;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macaria.app.models.BaseModel;
import com.macaria.app.repository.ProfileRepository;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class ContactUsViewModel extends ViewModel {
    private ProfileRepository repository;
    private MutableLiveData<BaseModel<ContactModel>> contactResponse = new MutableLiveData<>();
    private MutableLiveData<BaseModel> contactUs = new MutableLiveData<>();
    private MutableLiveData<String> errorMassage = new MutableLiveData<>();


    @ViewModelInject
    public ContactUsViewModel(ProfileRepository repository) {
        this.repository = repository;
    }

    public void requestContact() {
        repository.requestContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> contactResponse.setValue(response),
                        error ->
                                getErrorMessage(isHttpException(error)));
    }


    public MutableLiveData<BaseModel<ContactModel>> getContactResponse() {
        return contactResponse;
    }

    public MutableLiveData<BaseModel> getContactUs() {
        return contactUs;
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
        contactResponse = new MutableLiveData<>(); ;
    }

    public void contactUs(ContactUsRequest request) {
        repository.contactUs(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseModel model) {
                        contactUs.setValue(model);
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
