package com.macaria.app.ui.homeScreen.home.productsDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetails extends Fragment {
    ProductDetailsFragmentBinding binding ;

    @Inject
    MyHelper helper ;

    public ProductDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null){
            binding = ProductDetailsFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        binding.icBack.setOnClickListener(view -> back());

    }

    private void back() {
        Navigation.findNavController(requireView()).popBackStack();
    }
}