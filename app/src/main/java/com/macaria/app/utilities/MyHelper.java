package com.macaria.app.utilities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import javax.inject.Singleton;

import dagger.Provides;

public class MyHelper {

    @Provides
    @Singleton
    public void showErrorDialog(Context context, String title, String body) {
        Bundle bundle = new Bundle();
        if (title != null) {bundle.putString("title", title);}
        if (body != null) {bundle.putString("message", body);}
        FragmentActivity activity = (FragmentActivity) (context);
        FragmentManager fm = activity.getSupportFragmentManager();
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setArguments(bundle);
        errorDialog.show(fm, "fragment_alert");
    }
}
