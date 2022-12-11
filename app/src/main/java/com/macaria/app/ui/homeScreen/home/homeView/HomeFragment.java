package com.macaria.app.ui.homeScreen.home.homeView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.HomeFragmentBinding;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.ui.homeScreen.home.homeView.listeners.CategoriesListener;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;
import com.macaria.app.utilities.MyHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements CategoriesListener {
    private HomeFragmentBinding binding ;
    private HomeModel model ;

    @Inject
    MyHelper helper ;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = HomeFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){

    }

    private void initCategoryRec(){

    }

    @Override
    public void onCategorySelected(int id) {

    }
}