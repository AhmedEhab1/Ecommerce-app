package com.macaria.app.ui.homeScreen.categories.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CategoriesFragmentBinding;
import com.macaria.app.databinding.FilterFragmentBinding;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FilterFragment extends Fragment {
    FilterFragmentBinding binding ;

    @Inject
    MyHelper helper ;
    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = FilterFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){

    }
}