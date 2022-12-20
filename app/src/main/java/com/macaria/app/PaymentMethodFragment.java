package com.macaria.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.databinding.CartFragmentBinding;
import com.macaria.app.databinding.PaymentMethodFragmentBinding;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaymentMethodFragment extends Fragment {
    private int address_id ;
    private PaymentMethodFragmentBinding binding ;

    @Inject
    MyHelper helper ;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = PaymentMethodFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        getAddress();
    }

    private void getAddress(){
        if (getArguments() != null){
            address_id = getArguments().getInt("address_id");
        }
    }
}