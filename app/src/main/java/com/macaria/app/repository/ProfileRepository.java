package com.macaria.app.repository;

import com.macaria.app.models.BaseModel;
import com.macaria.app.network.ApiService;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.CitiesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.DeleteRequest;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class ProfileRepository {
    private ApiService apiService ;

    @Inject
    public ProfileRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<BaseModel<UserModel>> changeAccountInfoRequest(CreateAccountRequest request){
        return apiService.changeAccountInfo(request);
    }
    public Observable<BaseModel<UserModel>> changeAccountInfoRequest(CreateAccountRequest request, @Part MultipartBody.Part image){
        HashMap<String, String> map = new HashMap<>();
        map.put("first_name", request.getFirst_name());
        map.put("last_name", request.getLast_name());
        map.put("email", request.getEmail());
        return apiService.changeAccountInfo(map, image);
    }

    public Observable<BaseModel> updatePassword(ChangePasswordRequest request){
        return apiService.changePassword(request);
    }

    public Observable<BaseModel> deleteAddress(int id){
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(id);
        return apiService.deleteAddress(deleteRequest);
    }
    public Observable<BaseModel<List<AddressModel>>> getAddress(){
        return apiService.getAddress();
    }

    public Observable<BaseModel<ContactModel>> requestContact(){
        return apiService.requestContact();
    }

    public Observable<BaseModel<List<CitiesModel>>> getCities(){
        return apiService.getCities();
    }

    public Observable<BaseModel> addAddress(AddAddressRequest request){
        return apiService.addAddress(request);
    }

    public Observable<BaseModel> updateAddress(AddAddressRequest request){
        return apiService.updateAddress(request);
    }

    public Observable<BaseModel> contactUs(ContactUsRequest request){
        return apiService.contactUs(request);
    }

    public Observable<BaseModel<List<OrderHistoryModel>>> getOrderHistory(){
        return apiService.getOrderHistory();
    }

    public Observable<BaseModel> addReview(AddReviewRequest request){
        return apiService.addReview(request);
    }

}
