package com.macaria.app.ui.homeScreen.profile.savedAddresses.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.SavedAddressesFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters.AddressListener;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters.AllAddressAdapter;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.vm.SavedAddressViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SavedAddressesFragment extends Fragment implements AddressListener {
    private SavedAddressesFragmentBinding binding;
    private SavedAddressViewModel viewModel;
    private CartModel cartModel ;

    @Inject
    MyHelper helper;

    public SavedAddressesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SavedAddressesFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(SavedAddressViewModel.class);
        helper.showLoading(requireActivity());
        viewModel.getAddress();
        getAddressResponse();
        deleteAddressResponse();
        cartAddress();
        errorMessage();
        binding.addAddress.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_savedAddressesFragment_to_addAddressFragment));
    }

    private void getAddressResponse() {
        viewModel.getAddressModel().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<AddressModel>>>() {
            @Override
            public void onChanged(BaseModel<List<AddressModel>> listBaseModel) {
                helper.dismissLoading();
                initAddressRec(listBaseModel);
            }
        });
    }

    private void initAddressRec(BaseModel<List<AddressModel>> listBaseModel) {
        AllAddressAdapter addressAdapter = new AllAddressAdapter(getActivity(), this);
        binding.addAddressRec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.addAddressRec.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel.getItem().getData());
    }

    private void deleteAddressResponse(){
        viewModel.getDeleteAddressResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
            }
        });
    }

    @Override
    public void onDeleteAddress(int id) {
        helper.showLoading(requireActivity());
        viewModel.deleteAddress(id);
    }

    @Override
    public void onAddressClicked(int id, AddressModel model) {
        if (getArguments() != null) {
            Bundle args = new Bundle();
            args.putInt("address_id",id);
            cartModel.setAddress(model);
            args.putSerializable("cartModel", cartModel);
            Navigation.findNavController(requireView()).navigate(R.id.action_savedAddressesFragment_to_paymentMethodFragment, args);
        }
    }

    @Override
    public void onEditAddress(AddressModel model) {
        Bundle args = new Bundle();
        args.putSerializable("addressModel", model);
        Navigation.findNavController(requireView()).navigate(R.id.action_savedAddressesFragment_to_addAddressFragment, args);
    }

    private void errorMessage(){
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity() , null , s);
            }
        });
    }

    private void cartAddress(){
        if (getArguments() != null){
            binding.cartAddress.setVisibility(View.VISIBLE);
            cartModel = (CartModel) getArguments().getSerializable("cartModel");
        }
    }
}
