package com.macaria.app.ui.authorization;

import android.content.Context;
import android.util.Log;

import com.macaria.app.R;
import com.macaria.app.data.Constants;
import com.macaria.app.data.PreferencesHelper;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.UserModel;

public class AuthData {
    private Context context;

    public AuthData(Context context) {
        this.context = context;
    }

    public void SaveUserData(AuthModel authModel) {
        getPreferencesHelper().putString("token", authModel.getToken());
        saveUserData(authModel.getUser());
    }

    public void saveUserData(UserModel model) {
        getPreferencesHelper().putInt("userId", model.getId());
        getPreferencesHelper().putString("name", model.getName());
        getPreferencesHelper().putString("first_name", model.getFirstName());
        getPreferencesHelper().putString("last_name", model.getLastName());
        getPreferencesHelper().putString("mobile", model.getMobile());
        getPreferencesHelper().putString("email", model.getEmail());
        getPreferencesHelper().putString("image", model.getImage());
        getPreferencesHelper().putBoolean("is_verify", model.getIsVerify());
    }

    public AuthModel getUserData() {
        AuthModel model = new AuthModel();
        UserModel userModel = new UserModel();
        userModel.setId(getPreferencesHelper().getInt("userId"));
        model.setToken(getPreferencesHelper().getString("token"));
        Constants.TOKEN = getPreferencesHelper().getString("token");
        userModel.setName(getPreferencesHelper().getString("name"));
        userModel.setFirstName(getPreferencesHelper().getString("first_name"));
        userModel.setLastName(getPreferencesHelper().getString("last_name"));
        userModel.setMobile(getPreferencesHelper().getString("mobile"));
        userModel.setEmail(getPreferencesHelper().getString("email"));
        userModel.setImage(getPreferencesHelper().getString("image"));
        userModel.setIsVerify(getPreferencesHelper().getBoolean("is_verify"));
        model.setUser(userModel);
        Log.d("token", "getUserData: "+Constants.TOKEN);
        return model;
    }

    public PreferencesHelper getPreferencesHelper() {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE));
        return preferencesHelper;
    }

    public void logout(){
        getPreferencesHelper().removeKey("token");
        getPreferencesHelper().removeKey("userId");
        getPreferencesHelper().removeKey("name");
        getPreferencesHelper().removeKey("first_name");
        getPreferencesHelper().removeKey("last_name");
        getPreferencesHelper().removeKey("last_name");
        getPreferencesHelper().removeKey("mobile");
        getPreferencesHelper().removeKey("email");
        getPreferencesHelper().removeKey("image");
        getPreferencesHelper().removeKey("is_verify");
    }


}
