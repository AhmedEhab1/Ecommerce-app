package com.macaria.app.ui.homeScreen.categories.fragments;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.goodiebag.pinview.Pinview;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.macaria.app.R;
import com.macaria.app.databinding.ResetPasswordDialogBinding;
import com.macaria.app.databinding.SortByBinding;
import com.macaria.app.ui.authorization.forgetPassword.fragments.ResetPasswordListener;

public class SortByDialog {
    private SortByBinding binding;
    private BottomSheetDialog dialog;
    private SortByListener listener ;
    private boolean sendCode = false;
    // ClickHandler Build

    public SortByDialog(Context context, SortByListener listener) {
        dialog = new BottomSheetDialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        this.listener = listener ;
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = SortByBinding.inflate(LayoutInflater.from(context));

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init(){
        binding.close.setOnClickListener(view -> dialog.dismiss());
        binding.bestSelling.setOnClickListener(view -> listener.sortBy("best_seller"));
        binding.priceHighToLow.setOnClickListener(view -> listener.sortBy("high_price"));
        binding.priceLowToHigh.setOnClickListener(view -> listener.sortBy("less_price"));
        binding.newestItems.setOnClickListener(view -> listener.sortBy("new_arrivals"));
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
