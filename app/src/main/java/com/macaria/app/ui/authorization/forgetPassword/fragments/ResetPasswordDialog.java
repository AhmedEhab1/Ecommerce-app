package com.macaria.app.ui.authorization.forgetPassword.fragments;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.goodiebag.pinview.Pinview;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.macaria.app.R;
import com.macaria.app.databinding.ResetPasswordDialogBinding;

public class ResetPasswordDialog {
    private ResetPasswordDialogBinding binding;
    private BottomSheetDialog dialog;
    private ResetPasswordListener listener ;
    private String mobile ;
    private boolean sendCode = false;
    // ClickHandler Build

    public ResetPasswordDialog(Context context, ResetPasswordListener listener, String mobile) {
        dialog = new BottomSheetDialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        this.listener = listener ;
        this.mobile = mobile ;
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ResetPasswordDialogBinding.inflate(LayoutInflater.from(context));

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init(){
        binding.close.setOnClickListener(view -> dialog.dismiss());
        binding.sendCode.setOnClickListener(view ->  {if (sendCode)listener.onSendCodeClicked();});
        binding.mobile.setText(mobile);
        otpChangeListener();
        counter();
    }

    private void otpChangeListener(){
        binding.pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                listener.onOtpEntered(pinview.getValue());
            }
        });
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    private void counter() {
        sendCode = false;
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timer.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.timer.setText("00:00");
                sendCode = true;
            }
        }.start();
    }

}
