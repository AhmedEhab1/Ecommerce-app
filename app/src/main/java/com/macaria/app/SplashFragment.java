package com.macaria.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.macaria.app.databinding.SplashFragmentBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {
    private SplashFragmentBinding binding;

    @Inject
    Handler handler;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SplashFragmentBinding.inflate(inflater, container, false);
        handler.postDelayed(this::startProductsView, 1000);
        return binding.getRoot();
    }

    private void startProductsView() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }
}