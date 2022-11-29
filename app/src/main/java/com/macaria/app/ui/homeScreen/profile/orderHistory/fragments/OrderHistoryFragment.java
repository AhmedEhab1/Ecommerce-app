package com.macaria.app.ui.homeScreen.profile.orderHistory.fragments;

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
import com.macaria.app.databinding.OrderHistoryFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryAdapter;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryListener;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.OrderHistoryViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderHistoryFragment extends Fragment implements OrderHistoryListener {
    private OrderHistoryFragmentBinding binding;
    private OrderHistoryViewModel viewModel;

    @Inject
    MyHelper helper;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = OrderHistoryFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        requestOrderHistory();
        errorMessage();
    }

    private void requestOrderHistory() {
        viewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        helper.showLoading(requireActivity());
        viewModel.getOrderHistory();
        orderResponse();
    }

    private void orderResponse() {
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<OrderHistoryModel>>>() {
            @Override
            public void onChanged(BaseModel<List<OrderHistoryModel>> listBaseModel) {
                helper.dismissLoading();
                initOrderRec(listBaseModel);
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

    private void initOrderRec(BaseModel<List<OrderHistoryModel>> listBaseModel) {
        OrderHistoryAdapter addressAdapter = new OrderHistoryAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel.getItem().getData());
    }

    @Override
    public void onDetailsClicked(OrderHistoryModel model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        Navigation.findNavController(requireView()).navigate(R.id.action_orderHistoryFragment_to_orderDetailsFragment, bundle);
    }

    @Override
    public void onReOrderClicked(OrderHistoryModel model) {

    }
}