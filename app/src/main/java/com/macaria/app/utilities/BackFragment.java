package com.macaria.app.utilities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FragmentBackBinding;
import com.macaria.app.databinding.LoginFragmentBinding;

public class BackFragment extends Fragment {

    FragmentBackBinding binding ;
    public BackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentBackBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();

    }

    private void init(){
        binding.icBack.setOnClickListener(view -> {
            Log.d("icBack", "init: ");
            Navigation.findNavController(requireView()).popBackStack();
        });
    }
}