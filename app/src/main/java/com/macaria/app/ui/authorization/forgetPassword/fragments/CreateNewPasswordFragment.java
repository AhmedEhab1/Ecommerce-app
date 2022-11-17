package com.macaria.app.ui.authorization.forgetPassword.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CreateNewPasswordFragmentBinding;
import com.macaria.app.databinding.ForgetPasswordFragmentBinding;

public class CreateNewPasswordFragment extends Fragment {
    CreateNewPasswordFragmentBinding binding ;

    public CreateNewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CreateNewPasswordFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){

    }
}