package com.macaria.app.ui.authorization;

import android.content.Context;

import com.macaria.app.R;
import com.macaria.app.data.PreferencesHelper;
import com.macaria.app.ui.authorization.login.model.AuthModel;

public class AuthData {
    private Context context ;

    public AuthData(Context context) {
        this.context = context;
    }

    public void SaveUserData(AuthModel authModel){
        getPreferencesHelper().putString("token",authModel.getToken());
        getPreferencesHelper().putInt("userId",authModel.getUser().getId());
        getPreferencesHelper().putString("name",authModel.getUser().getName());
        getPreferencesHelper().putString("first_name",authModel.getUser().getFirstName());
        getPreferencesHelper().putString("last_name",authModel.getUser().getLastName());
        getPreferencesHelper().putString("mobile",authModel.getUser().getMobile());
        getPreferencesHelper().putString("email",authModel.getUser().getEmail());
        getPreferencesHelper().putString("image",authModel.getUser().getImage());
        getPreferencesHelper().putBoolean("is_verify",authModel.getUser().getIsVerify());
    }

    public PreferencesHelper getPreferencesHelper() {
        PreferencesHelper preferencesHelper = new PreferencesHelper(context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE));
        return preferencesHelper;
    }


}
