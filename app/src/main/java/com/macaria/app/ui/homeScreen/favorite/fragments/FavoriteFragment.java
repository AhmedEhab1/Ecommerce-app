package com.macaria.app.ui.homeScreen.favorite.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.favorite.vm.FavoriteViewModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment implements ProductsListener {
    private FavoriteFragmentBinding binding;
    private FavoriteViewModel viewModel;
    private ProductsAdapter productsAdapter;

    @Inject
    MyHelper helper;

    @Inject
    SetFavoriteRequest favoriteRequest;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = FavoriteFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
        dataVisibility();
    }

    private void requestFavorite() {
        if (viewModel.getModelMutableLiveData().getValue() == null) {
            helper.showLoading(requireActivity());
            viewModel.getFavorite();
            if (productsAdapter != null) productsAdapter.clear();
        }
        productsResponse();
    }

    private void productsResponse() {
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<ProductModel>>>() {
            @Override
            public void onChanged(BaseModel<List<ProductModel>> listBaseModel) {
                helper.dismissLoading();
                initProductsRec(listBaseModel);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initProductsRec(BaseModel<List<ProductModel>> listBaseModel) {
        if (listBaseModel.getItem().getData().size() == 0) {
            binding.emptyProducts.setVisibility(View.VISIBLE);
            binding.exploreProducts.setVisibility(View.VISIBLE);
        } else binding.emptyProducts.setVisibility(View.GONE);
        productsAdapter = new ProductsAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recycler.setAdapter(productsAdapter);
        productsAdapter.addData(listBaseModel.getItem().getData());
    }

    private void dataVisibility() {
        AuthData authData = new AuthData(requireActivity());
        if (authData.getUserData().getToken().equals("")) {
            binding.emptyProducts.setVisibility(View.VISIBLE);
            binding.loginRegister.setVisibility(View.VISIBLE);
            binding.loginRegister.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_global_login));
        } else {
            requestFavorite();
            errorMessage();
            swipeToRefresh();
            exploreProducts();
        }
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
    public void onProductClick(ProductModel model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProductModel", model);
        bundle.putString("type", "model");
        Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_product_details, bundle);
    }

    private void exploreProducts() {
        binding.exploreProducts.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("category_id", 0);
            bundle.putString("title", "");
            Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_filterFragment, bundle);
        });
    }

    @Override
    public void onFavoriteClick(int id) {
        helper.showLoading(requireActivity());
        favoriteRequest.setProduct_id(id);
        viewModel.setFavorite(favoriteRequest);
        getSetFavoriteResponse();
    }

    private void getSetFavoriteResponse() {
        viewModel.getSetFavorite().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                requestFavorite();
            }
        });
    }

    private void swipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(this::resetList);
    }

    private void resetList() {
        helper.showLoading(requireActivity());
        viewModel.getFavorite();
        if (productsAdapter != null) productsAdapter.clear();
        productsResponse();

        //        productsAdapter.setFinishedLoading(false);
//        currentPage = 1;
//        endlessRecyclerViewScrollListener.resetState();
    }


}