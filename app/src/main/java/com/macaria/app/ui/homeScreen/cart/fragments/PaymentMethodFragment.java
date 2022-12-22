package com.macaria.app.ui.homeScreen.cart.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.PaymentTokenModel;
import com.macaria.app.ui.homeScreen.cart.model.StoreOrderRequest;
import com.macaria.app.ui.homeScreen.cart.vm.OrderSummeryViewModel;
import com.macaria.app.utilities.MyHelper;
import com.paymob.acceptsdk.IntentConstants;
import com.paymob.acceptsdk.PayActivity;
import com.paymob.acceptsdk.PayActivityIntentKeys;
import com.paymob.acceptsdk.PayResponseKeys;
import com.paymob.acceptsdk.SaveCardResponseKeys;
import com.paymob.acceptsdk.ThreeDSecureWebViewActivty;
import com.paymob.acceptsdk.ToastMaker;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PaymentMethodFragment extends Fragment {
    private int address_id ;
    private PaymentMethodFragmentBinding binding ;
    private CartModel cartModel ;
    private boolean invoiceState = false ;
    private OrderSummeryViewModel viewModel ;
    private int ACCEPT_PAYMENT_REQUEST = 99 ;
    private String paymentKey ;

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
        viewModel = new ViewModelProvider(requireActivity()).get(OrderSummeryViewModel.class);
        getAddress();
        showInvoiceData();
        onCashClicked();
        errorMessage();
        binding.paymob.setOnClickListener(view -> getToken());

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

    private void getToken(){
        helper.showLoading(requireActivity());
        viewModel.getPaymentToken();
        viewModel.getPaymentMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<PaymentTokenModel>>() {
            @Override
            public void onChanged(BaseModel<PaymentTokenModel> paymentTokenModelBaseModel) {
                helper.dismissLoading();
                paymentKey = paymentTokenModelBaseModel.getItem().getData().getKey().getToken();
                payMobSDK();
            }
        });
    }



    private void payMobSDK(){
        Intent pay_intent = new Intent(getActivity(), PayActivity.class);
        putNormalExtras(pay_intent);
        //this key is used to set the theme color(Actionbar, statusBar, button).
        pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);

        pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR,getResources().getColor(R.color.colorPrimary));

        // this key is to wether display the Actionbar or not.
        pay_intent.putExtra("ActionBar",false);

        // this key is used to define the language. takes for ex ("ar", "en") as inputs.
        pay_intent.putExtra("language","en");

        startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
        Intent secure_intent = new Intent(requireActivity(), ThreeDSecureWebViewActivty.class);
        secure_intent.putExtra("ActionBar",true);
    }

    private void putNormalExtras(Intent intent) {
        intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
        intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE, "Verification");
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void confirmPayment(){
        helper.showLoading(requireActivity());
        StoreOrderRequest request = new StoreOrderRequest();
        request.setPayment_method("credit");
        request.setAddress_id(address_id);
        viewModel.storeOrder(request);
        storeOrderResponse();
    }

    private void storeOrderResponse(){
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> model) {
                helper.dismissLoading();
                Bundle args = new Bundle();
                args.putSerializable("cartModel", model.getItem().getData());
                Navigation.findNavController(requireView()).navigate(R.id.action_paymentMethodFragment_to_orderCompletedFragment, args);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();

        if (requestCode == ACCEPT_PAYMENT_REQUEST) {

            if (resultCode == IntentConstants.USER_CANCELED) {
                // User canceled and did no payment request was fired
                ToastMaker.displayShortToast(requireActivity(), "User canceled!!");
            } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
                // You forgot to pass an important key-value pair in the intent's extras
                ToastMaker.displayShortToast(requireActivity(), "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
            } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
                // An error occurred while handling an API's response
                ToastMaker.displayShortToast(requireActivity(), "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
                // User attempted to pay but their transaction was rejected

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras.getString(PayResponseKeys.DATA_MESSAGE));
            } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
                // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
                ToastMaker.displayShortToast(requireActivity(), extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
                // User finished their payment successfully
                confirmPayment();
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras.getString(PayResponseKeys.DATA_MESSAGE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(requireActivity(), "TRANSACTION_SUCCESSFUL - Parsing Issue");

                // ToastMaker.displayShortToast(this, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
                // User finished their payment successfully and card was saved.

                // Use the static keys declared in PayResponseKeys to extract the fields you want
                // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), "Token == " + extras.getString(SaveCardResponseKeys.TOKEN));
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
                ToastMaker.displayShortToast(requireActivity(), "User canceled 3-d scure verification!!");

                // Note that a payment process was attempted. You can extract the original returned values
                // Use the static keys declared in PayResponseKeys to extract the fields you want
                ToastMaker.displayShortToast(requireActivity(), extras.getString(PayResponseKeys.PENDING));
            } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
                ToastMaker.displayShortToast(requireActivity(), "User canceled 3-d scure verification - Parsing Issue!!");

                // Note that a payment process was attempted.
                // User finished their payment successfully. An error occured while reading the returned JSON.
                ToastMaker.displayShortToast(requireActivity(), extras.getString(IntentConstants.RAW_PAY_RESPONSE));
            }
        }
    }
}