package com.macaria.app.utilities.errorDialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


import com.macaria.app.R;

import java.util.Objects;

public class ErrorDialog extends DialogFragment {
    TextView title, body, cancel, done;
    ErrorDialogListener listener  ;

    public ErrorDialog() {
    }

    public ErrorDialog(ErrorDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogStyle;
        getDialog().getWindow().setGravity(Gravity.CENTER);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
        View root = inflater.inflate(R.layout.fragment_error_dialog, container, false);
        // Inflate the layout for this fragment
        title = root.findViewById(R.id.title);
        body = root.findViewById(R.id.body);
        done = root.findViewById(R.id.confirm);
        cancel = root.findViewById(R.id.cancel);

        String message = null;
        String titleText = null;
        if (getArguments() != null) {
            message = getArguments().getString("message");
            body.setText(message);
            if (getArguments().getString("title") != null) {
                titleText = getArguments().getString("title");
                title.setText(titleText);
            }
        } else {
            message = getActivity().getResources().getString(R.string.error_message);
            body.setText(message);
        }

        if (listener != null){
            done.setVisibility(View.VISIBLE);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getDialog()).dismiss();
                listener.onConfirm();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });
        return root;
    }
}