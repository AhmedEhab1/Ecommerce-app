package com.macaria.app.ui.homeScreen.profile.savedAddresses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.ChangePasswordFragmentBinding;
import com.macaria.app.databinding.SavedAddressesFragmentBinding;


public class SavedAddressesFragment extends Fragment {
    private SavedAddressesFragmentBinding binding;


    public SavedAddressesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SavedAddressesFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

    }
}
