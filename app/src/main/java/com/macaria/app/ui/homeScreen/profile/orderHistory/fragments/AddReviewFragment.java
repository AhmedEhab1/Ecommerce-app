package com.macaria.app.ui.homeScreen.profile.orderHistory.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.AddReviewFragmentBinding;
import com.macaria.app.databinding.OrderDetailsFragmentBinding;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddReviewFragment extends Fragment {
    AddReviewFragmentBinding binding ;

    @Inject
    MyHelper helper;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddReviewFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){

    }

}