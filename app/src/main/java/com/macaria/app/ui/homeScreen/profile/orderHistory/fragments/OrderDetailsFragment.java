package com.macaria.app.ui.homeScreen.profile.orderHistory.fragments;

import static com.macaria.app.data.Constants.CANCEL;
import static com.macaria.app.data.Constants.DELIVERY;
import static com.macaria.app.data.Constants.PENDING;
import static com.macaria.app.data.Constants.PREPARING;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.OrderDetailsFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryAdapter;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderProductsAdapter;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.ProductsListener;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderDetailsFragment extends Fragment implements ProductsListener {
    private OrderDetailsFragmentBinding binding;
    private OrderHistoryModel model;

    @Inject
    MyHelper helper;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = OrderDetailsFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        getOrderModel();
        setAddressModel();
        setDetailsData();
        setPriceData();
        initProductRec();
        setStatusColor();
    }

    private void getOrderModel() {
        if (getArguments() != null) {
            this.model = (OrderHistoryModel) getArguments().getSerializable("model");
        }
    }

    private void setAddressModel() {
        AddressModel addressModel = model.getAddress().getData();
        binding.userName.setText(addressModel.getFirstName().concat(" ").concat(addressModel.getLastName()));
        binding.address.setText(addressModel.getAddress());
        binding.city.setText(addressModel.getCity());
        binding.country.setText(addressModel.getCountry());
        binding.phone.setText(addressModel.getPhone());
    }

    private void setDetailsData() {
        binding.orderReference.setText("#".concat(model.getOrderNumber()));
        binding.placedOn.setText(model.getDate());
        binding.payment.setText(model.getPaymentType());
    }

    private void setPriceData() {
        binding.subTotal.setText(model.getSubTotal().concat(" ").concat(getString(R.string.egp)));
        binding.shipping.setText(model.getDeliveryFee().concat(" ").concat(getString(R.string.egp)));
        binding.taxes.setText("0 ".concat(getString(R.string.egp)));
        binding.total.setText(model.getTotalPrice().concat(" ").concat(getString(R.string.egp)));
    }

    private void initProductRec() {
        OrderProductsAdapter adapter = new OrderProductsAdapter(getActivity(), this, model.getStatus());
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        adapter.addData(model.getOrderDetails().getData());
    }

    private void setStatusColor(){
        String status = model.getStatus();
        binding.status.setText(status);
        if (status.equals(PENDING)) {
            binding.status.setTextColor(ContextCompat.getColor(requireActivity(), R.color.lightGreyColor));
           binding.cardView.setCardBackgroundColor(Color.parseColor("#33919699"));
        } else if (status.equals(PREPARING)) {
            binding.status.setTextColor(ContextCompat.getColor(requireActivity(), R.color.orangeColor));
            binding.cardView.setCardBackgroundColor(Color.parseColor("#33F3AA18"));
        } else if (status.equals(DELIVERY)) {
            binding.status.setTextColor(ContextCompat.getColor(requireActivity(), R.color.greenColor));
            binding.cardView.setCardBackgroundColor(Color.parseColor("#332FA84F"));
        } else if (status.equals(CANCEL)) {
            binding.status.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red));
            binding.cardView.setCardBackgroundColor(Color.parseColor("#33EA3D2F"));
        }
    }

    @Override
    public void onReviewItemClicked() {
        Navigation.findNavController(requireView()).navigate(R.id.action_orderDetailsFragment_to_addReviewFragment);

    }
}