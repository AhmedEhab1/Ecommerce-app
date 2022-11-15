package com.macaria.app.utilities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;

public class MyHelper {
    Loading loading  = new Loading();


    public void showErrorDialog(Context context, String title, String body) {
        loading.dismiss();
        Bundle bundle = new Bundle();
        if (title != null) {bundle.putString("title", title);}
        if (body != null) {bundle.putString("message", body);}
        FragmentActivity activity = (FragmentActivity) (context);
        FragmentManager fm = activity.getSupportFragmentManager();
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setArguments(bundle);
        errorDialog.show(fm, "fragment_alert");
    }

    public void showLoading(Context context){
        FragmentActivity activity = (FragmentActivity) (context);
        FragmentManager fm = activity.getSupportFragmentManager();
        loading.show(fm, "fragment_alert");
    }

    public void dismissLoading(){
        loading.dismiss();
    }
}
