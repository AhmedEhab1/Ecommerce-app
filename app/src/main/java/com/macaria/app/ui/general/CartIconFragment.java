package com.macaria.app.ui.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.macaria.app.R;
import com.macaria.app.databinding.CartIconFragmentBinding;
import com.macaria.app.databinding.FragmentBackBinding;

public class CartIconFragment extends Fragment {
    private CartIconFragmentBinding binding ;

    public CartIconFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = CartIconFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();

    }

    private void init(){
        binding.itemView.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_global_cartFragment);
        });
    }
}