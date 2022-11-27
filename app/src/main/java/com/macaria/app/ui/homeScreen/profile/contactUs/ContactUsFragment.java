package com.macaria.app.ui.homeScreen.profile.contactUs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.databinding.ContactUsFragmentBinding;
import com.macaria.app.databinding.SplashFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContactUsFragment extends Fragment {
    private ContactUsFragmentBinding binding ;


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ContactUsFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){

    }
}