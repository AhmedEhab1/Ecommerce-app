package com.macaria.app.ui.homeScreen.profile.savedAddresses.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.AddAddressFragmentBinding;
import com.macaria.app.databinding.SavedAddressesFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddAddressFragment extends Fragment {
    private AddAddressFragmentBinding binding ;

    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddAddressFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        
    }
}