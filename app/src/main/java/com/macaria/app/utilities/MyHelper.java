package com.macaria.app.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.macaria.app.utilities.errorDialog.ErrorDialog;

public class MyHelper {
    Loading loading = new Loading();


    public void showErrorDialog(Context context, String title, String body) {
        if (loading.getDialog() != null) loading.dismiss();

        Bundle bundle = new Bundle();
        if (title != null) {
            bundle.putString("title", title);
        }
        if (body != null) {
            bundle.putString("message", body);
        }
        FragmentActivity activity = (FragmentActivity) (context);
        FragmentManager fm = activity.getSupportFragmentManager();
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setArguments(bundle);
        errorDialog.show(fm, "fragment_alert");
    }

    public void showLoading(Context context) {
        FragmentActivity activity = (FragmentActivity) (context);
        FragmentManager fm = activity.getSupportFragmentManager();
        loading.show(fm, "fragment_alert");
    }

    public void dismissLoading() {
        loading.dismiss();
    }

    public void showSnackBar(Context context, String message) {
        Snackbar.make((((Activity) context).findViewById(android.R.id.content)), message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                .setTextColor(context.getResources().getColor(android.R.color.white))
                .show();
    }

    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
