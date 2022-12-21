package com.macaria.app.ui.homeScreen.cart.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.PaymentMethodFragmentBinding;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaymentMethodFragment extends Fragment {
    private int address_id ;
    private PaymentMethodFragmentBinding binding ;
    private CartModel cartModel ;
    private boolean invoiceState = false ;

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
        showInvoiceData();
        onCashClicked();
    }

    private void getAddress(){
        if (getArguments() != null){
            address_id = getArguments().getInt("address_id");
            cartModel = (CartModel) getArguments().getSerializable("cartModel");
            setInvoiceData();
        }
    }

    private void setInvoiceData(){
        binding.totalItems.setText(String.valueOf(cartModel.getItemCount()).concat(" ").concat(getString(R.string.item_count)));
        binding.totalItemsPrice.setText(cartModel.getTotalPrice().concat(" ").concat(getString(R.string.egp)));
        binding.totalPrice.setText(cartModel.getTotalPrice().concat(" ").concat(getString(R.string.egp)));
        binding.subtotalPrice.setText(cartModel.getSubTotal().concat(" ").concat(getString(R.string.egp)));
        binding.shippingPrice.setText(cartModel.getDeliveryFee().concat(" ").concat(getString(R.string.egp)));
        binding.taxesPrice.setText("0 ".concat(getString(R.string.egp)));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showInvoiceData(){
        binding.invoiceDataLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideBottomNavBar(invoiceState);
                return false;
            }
        });
    }

    private void hideBottomNavBar(boolean show) {
        Transition transition = new Fade();
        transition.setDuration(400);
        transition.addTarget(binding.invoiceData);
        TransitionManager.beginDelayedTransition(binding.invoiceData, transition);
        binding.invoiceData.setVisibility(show ? View.VISIBLE : View.GONE);
        invoiceState = !invoiceState;
    }

    private void onCashClicked(){
        binding.cashOnDelivery.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putSerializable("cartModel", cartModel);
            Navigation.findNavController(requireView()).navigate(R.id.action_paymentMethodFragment_to_orderSummeryFragment, args);
        });
    }
}