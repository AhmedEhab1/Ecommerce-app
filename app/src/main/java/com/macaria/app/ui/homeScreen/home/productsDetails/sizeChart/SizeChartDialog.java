package com.macaria.app.ui.homeScreen.home.productsDetails.sizeChart;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.macaria.app.R;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.databinding.SizeChartDialogBinding;


public class SizeChartDialog extends DialogFragment {
    private SizeChartDialogBinding binding ;
    private String imageUrl ;

    public SizeChartDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
        binding = SizeChartDialogBinding.inflate(inflater, container, false);
        loadSizeImage();
        binding.icBack.setOnClickListener(view -> closeFragment());
        return binding.getRoot();
    }

    private void loadSizeImage(){
        if (getArguments()!= null){
            imageUrl = getArguments().getString("image");
            loadImage(getActivity(), imageUrl, R.drawable.load_icon, binding.image);
        }
    }

    public void closeFragment(){
        this.getDialog().dismiss();
    }
}