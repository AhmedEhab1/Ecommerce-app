package com.macaria.app.ui.general.backFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.macaria.app.databinding.CloseFragmentBinding;
import com.macaria.app.databinding.FragmentBackBinding;

public class CloseFragment extends Fragment {
    CloseFragmentBinding binding ;

    public CloseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = CloseFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();

    }

    private void init(){
        binding.icBack.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).popBackStack();
        });
    }
}