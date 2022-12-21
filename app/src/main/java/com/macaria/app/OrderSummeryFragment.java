package com.macaria.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.databinding.CartFragmentBinding;
import com.macaria.app.databinding.OrderDetailsFragmentBinding;
import com.macaria.app.databinding.OrderSummeryFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.cart.adapter.CartProductsAdapter;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderSummeryFragment extends Fragment {
    private OrderSummeryFragmentBinding binding ;
    private CartModel model ;

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
        getData();
        setAddressModel();
        initRec();
    }

    private void setAddressModel() {
        AddressModel addressModel = model.getAddress();
        binding.userName.setText(addressModel.getFirstName().concat(" ").concat(addressModel.getLastName()));
        binding.address.setText(addressModel.getAddress());
        binding.city.setText(addressModel.getCity());
        binding.country.setText(addressModel.getCountry());
        binding.phone.setText(addressModel.getPhone());
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

}