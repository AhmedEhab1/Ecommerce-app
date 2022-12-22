package com.macaria.app.ui.homeScreen.cart.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;

import com.macaria.app.databinding.OrderCompletedFragmentBinding;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderCompletedFragment extends Fragment {
    private OrderCompletedFragmentBinding binding ;
    private CartModel model ;

    public OrderCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = OrderCompletedFragmentBinding.inflate(inflater, container, false);
            init();
        return binding.getRoot();
    }

    private void init(){
        getData();
        onTrackOrderClicked();
    }

    private void getData(){
        if (getArguments() != null) model = (CartModel) getArguments().getSerializable("cartModel");
        binding.orderNumber.setText(String.valueOf(model.getOrderNumber()));
    }

    private void onTrackOrderClicked(){
        binding.trackOrder.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_orderCompletedFragment_to_orderHistoryFragment);
        });
    }
}