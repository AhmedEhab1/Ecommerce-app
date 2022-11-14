package com.macaria.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.macaria.app.databinding.SplashFragmentBinding;

public class SplashFragment extends Fragment {
    private SplashFragmentBinding binding;


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SplashFragmentBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        Glide.with(this)
                .load(R.raw.splash)
                .into(binding.imageView);

        return binding.getRoot();
    }
}