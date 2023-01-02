package com.macaria.app.ui.homeScreen.categories.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.macaria.app.R;
import com.macaria.app.databinding.FilterDialogBinding;
import com.macaria.app.databinding.SortByBinding;

public class FilterDialog {
    private FilterDialogBinding binding;
    private BottomSheetDialog dialog;
    private SortByListener listener ;
    private boolean sendCode = false;
    // ClickHandler Build

    public FilterDialog(Context context, SortByListener listener) {
        dialog = new BottomSheetDialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        this.listener = listener ;
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = FilterDialogBinding.inflate(LayoutInflater.from(context));

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init(){

    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
