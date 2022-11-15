package com.macaria.app.ui.authorization.createAccount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CreareAccountFragmentBinding;
import com.macaria.app.databinding.LoginFragmentBinding;


public class CreateAccountFragment extends Fragment {
    CreareAccountFragmentBinding binding;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = CreareAccountFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.login.setOnClickListener(view -> Navigation.findNavController(requireView()).popBackStack());
    }
}