package com.macaria.app.ui.homeScreen.categories.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.macaria.app.R;
import com.macaria.app.data.HomeData;
import com.macaria.app.databinding.FilterDialogBinding;
import com.macaria.app.ui.homeScreen.categories.adapters.FilterCategoryAdapter;
import com.macaria.app.ui.homeScreen.categories.adapters.FilterColorAdapter;
import com.macaria.app.ui.homeScreen.categories.adapters.FilterSizeAdapter;

import java.util.HashMap;
import java.util.Map;

public class FilterDialog implements FilterDialogListener {
    private FilterDialogBinding binding;
    private BottomSheetDialog dialog;
    private FilterListener listener;
    private boolean sendCode = false;
    private Context context;
    private int categoryId, sizeId , colorId , priceFrom = 0, priceTo = 2500;
    private Map<String, Object> params = new HashMap<String, Object>();

    // ClickHandler Build
    public FilterDialog(Context context, FilterListener listener) {
        this.context = context;
        dialog = new BottomSheetDialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        this.listener = listener;
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = FilterDialogBinding.inflate(LayoutInflater.from(context));

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        initCategoryRec();
        priceRange();
        viewResult();
        binding.clear.setOnClickListener(view -> clearData());
        binding.styleRecImage.setOnClickListener(view -> showView(binding.styleRec, binding.styleRecImage));
        binding.colorRecImage.setOnClickListener(view -> {
            showView(binding.colorRec, binding.colorRecImage);
            initColorsRec();
        });
        binding.sizeRecImage.setOnClickListener(view -> {
            showView(binding.sizeRec, binding.sizeRecImage);
            initSizeRec();
        });
        binding.priceLayoutImage.setOnClickListener(view -> {
            showView(binding.priceLayout, binding.priceLayoutImage);
        });
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    private void initCategoryRec() {
        FilterCategoryAdapter productsAdapter = new FilterCategoryAdapter(context, this);
        binding.styleRec.setLayoutManager(new GridLayoutManager(context, 2));
        binding.styleRec.setAdapter(productsAdapter);
        productsAdapter.addData(HomeData.getInstance().getCategoriesModel());
    }

    private void initColorsRec() {
        FilterColorAdapter filterColorAdapter = new FilterColorAdapter(context, this);
        binding.colorRec.setLayoutManager(new GridLayoutManager(context, 2));
        binding.colorRec.setAdapter(filterColorAdapter);
        filterColorAdapter.addData(HomeData.getInstance().getColorModels());
    }

    private void initSizeRec() {
        FilterSizeAdapter filterColorAdapter = new FilterSizeAdapter(context, this);
        binding.sizeRec.setLayoutManager(new GridLayoutManager(context, 2));
        binding.sizeRec.setAdapter(filterColorAdapter);
        filterColorAdapter.addData(HomeData.getInstance().getSizeModels());
    }

    private void clearData() {
        initCategoryRec();
        initColorsRec();
        initSizeRec();
    }

    private void showView(View view, ImageView imageView) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.ic_add_address);
        } else {
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_sub_item_cart);
        }
    }

    @Override
    public void onCategoryClicked(int categoryId) {
        this.categoryId = categoryId ;
    }

    @Override
    public void onSizeClicked(int sizeId) {
        this.sizeId = sizeId ;
    }

    @Override
    public void onColorClicked(int colorId) {
        this.colorId = colorId ;
    }

    private void viewResult(){
        binding.viewResults.setOnClickListener(view -> {
            params.put("category_id", categoryId);
            params.put("size_id", sizeId);
            params.put("color_id", colorId);
            params.put("price_from", priceFrom);
            params.put("price_to", priceTo);
            listener.onViewResultClicked(params);
        });
    }

    private void priceRange(){
        binding.rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                priceFrom = binding.rangeSlider.getValues().get(0).intValue() ;
                priceTo = binding.rangeSlider.getValues().get(1).intValue() ;
                binding.from.setText(String.valueOf(priceFrom));
                binding.to.setText(String.valueOf(priceTo));
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                priceFrom = binding.rangeSlider.getValues().get(0).intValue() ;
                priceTo = binding.rangeSlider.getValues().get(1).intValue() ;
                binding.from.setText(String.valueOf(priceFrom));
                binding.to.setText(String.valueOf(priceTo));
            }
        });
    }
}
