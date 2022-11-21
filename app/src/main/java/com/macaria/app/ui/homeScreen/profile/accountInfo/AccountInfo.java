package com.macaria.app.ui.homeScreen.profile.accountInfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.databinding.AccountInfoFragmentBinding;


public class AccountInfo extends Fragment {
    AccountInfoFragmentBinding binding ;

    public AccountInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AccountInfoFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
    }
}