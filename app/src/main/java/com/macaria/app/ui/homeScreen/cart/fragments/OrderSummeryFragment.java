package com.macaria.app.ui.homeScreen.cart.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CartFragmentBinding;
import com.macaria.app.databinding.OrderDetailsFragmentBinding;
import com.macaria.app.databinding.OrderSummeryFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.cart.adapter.CartProductsAdapter;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.cart.model.StoreOrderRequest;
import com.macaria.app.ui.homeScreen.cart.vm.OrderSummeryViewModel;
import com.macaria.app.ui.homeScreen.favorite.vm.FavoriteViewModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderSummeryFragment extends Fragment {
    private OrderSummeryFragmentBinding binding ;
    private CartModel model ;
    private OrderSummeryViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public OrderSummeryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = OrderSummeryFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(requireActivity()).get(OrderSummeryViewModel.class);
        getData();
        setAddressModel();
        initRec();
        confirmPayment();
        errorMessage();
    }

    private void setAddressModel() {
        AddressModel addressModel = model.getAddress();
        binding.userName.setText(addressModel.getFirstName().concat(" ").concat(addressModel.getLastName()));
        binding.address.setText(addressModel.getAddress());
        binding.city.setText(addressModel.getCity());
        binding.country.setText(addressModel.getCountry());
        binding.phone.setText(addressModel.getPhone());
        binding.totalPrice.setText(model.getTotalPrice().concat(" ").concat(getString(R.string.egp)));

    }

    private void getData(){
        if (getArguments() != null) model = (CartModel) getArguments().getSerializable("cartModel");
    }

    private void initRec() {
        CartProductsAdapter adapter = new CartProductsAdapter(getActivity());
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        adapter.addData(model.getCartProductsModel().getData());
    }

    private void confirmPayment(){
        binding.confirmPayment.setOnClickListener(view -> {
            helper.showLoading(requireActivity());
            StoreOrderRequest request = new StoreOrderRequest();
            request.setPayment_method("cash");
            request.setAddress_id(model.getAddress().getId());
            viewModel.storeOrder(request);
            storeOrderResponse();
        });
    }

    private void storeOrderResponse(){
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> model) {
                helper.dismissLoading();
                Bundle args = new Bundle();
                args.putSerializable("cartModel", model.getItem().getData());
                Navigation.findNavController(requireView()).navigate(R.id.action_orderSummeryFragment_to_orderCompletedFragment, args);
            }
        });
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

}