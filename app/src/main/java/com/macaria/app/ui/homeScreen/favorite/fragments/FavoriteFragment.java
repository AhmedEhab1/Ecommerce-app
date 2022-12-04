package com.macaria.app.ui.homeScreen.favorite.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FaqFragmentBinding;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.favorite.vm.FavoriteViewModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryAdapter;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.OrderHistoryViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment implements ProductsListener {
    private FavoriteFragmentBinding binding ;
    private FavoriteViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null){
            binding = FavoriteFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        requestFavorite();
        errorMessage();
    }

    private void requestFavorite() {
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        helper.showLoading(requireActivity());
        viewModel.getFavorite();
        orderResponse();
    }

    private void orderResponse() {
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<ProductModel>>>() {
            @Override
            public void onChanged(BaseModel<List<ProductModel>> listBaseModel) {
                helper.dismissLoading();
                initOrderRec(listBaseModel);
            }
        });
    }

    private void initOrderRec(BaseModel<List<ProductModel>> listBaseModel) {
        ProductsAdapter addressAdapter = new ProductsAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recycler.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel.getItem().getData());
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    @Override
    public void onProductClick(int id) {

    }

    @Override
    public void onFavoriteClick(int id) {
        helper.showLoading(requireActivity());
        SetFavoriteRequest request = new SetFavoriteRequest();
        request.setProduct_id(id);
        viewModel.setFavorite(request);
        getSetFavoriteResponse();
    }

    private void getSetFavoriteResponse(){
        viewModel.getSetFavorite().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                requestFavorite();
            }
        });
    }
}