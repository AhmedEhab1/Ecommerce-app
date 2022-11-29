package com.macaria.app.ui.homeScreen.favorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FaqFragmentBinding;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment {
    FavoriteFragmentBinding binding ;

    @Inject
    MyHelper helper ;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){

    }
}