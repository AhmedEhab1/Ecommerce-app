package com.macaria.app.ui.homeScreen.categories.fragments;

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
import com.macaria.app.databinding.FilterFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.categories.models.PagesRequest;
import com.macaria.app.ui.homeScreen.categories.vm.FilterViewModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.utilities.MyHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FilterFragment extends Fragment implements ProductsListener {
    private FilterFragmentBinding binding ;
    private int category_id ;
    private FilterViewModel viewModel ;
    private PagesRequest request = new PagesRequest();

    @Inject
    MyHelper helper ;

    @Inject
    SetFavoriteRequest favoriteRequest;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FilterFragmentBinding.inflate(inflater, container, false);
        init();

        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
        getData();
        errorMessage();
        pagesResponse();
    }

    private void getData(){
        if (getArguments() != null){
            category_id = getArguments().getInt("category_id");
            String title = getArguments().getString("title") ;
            binding.title.setText(title);
            if (viewModel.getProductsMutableLiveData().getValue() == null){
                requestData();
            }
        }
    }

    private void requestData(){
        helper.showLoading(requireActivity());
        request.setCategory_id(category_id);
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("category_id", category_id);
        viewModel.getPages(params);
    }

    private void pagesResponse(){
        viewModel.getProductsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<ProductModel>>>() {
            @Override
            public void onChanged(BaseModel<List<ProductModel>> listBaseModel) {
                helper.dismissLoading();
                initProductsRec(listBaseModel);
            }
        });
    }

    private void initProductsRec(BaseModel<List<ProductModel>> listBaseModel) {
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recycler.setAdapter(productsAdapter);
        productsAdapter.addData(listBaseModel.getItem().getData());
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
        Navigation.findNavController(requireView()).navigate(R.id.action_filterFragment_to_product_details, bundle);
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
                helper.dismissLoading();
            }
        });
    }
}