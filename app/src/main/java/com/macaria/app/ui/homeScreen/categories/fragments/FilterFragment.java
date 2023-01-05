package com.macaria.app.ui.homeScreen.categories.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FilterFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.fragments.ResetPasswordDialog;
import com.macaria.app.ui.homeScreen.categories.models.PagesRequest;
import com.macaria.app.ui.homeScreen.categories.vm.FilterViewModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsHorizontalAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.utilities.MyHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FilterFragment extends Fragment implements ProductsListener, SortByListener , FilterListener{
    private FilterFragmentBinding binding;
    private int category_id;
    private FilterViewModel viewModel;
    private PagesRequest request = new PagesRequest();
    private String title;
    private BaseModel<List<ProductModel>> productsList;
    private boolean vertical = true;
    private SortByDialog sortByDialog;
    private FilterDialog filterDialog;
    private Map<String, Object> params = new HashMap<String, Object>();

    @Inject
    MyHelper helper;

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

    private void init() {
        viewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
        filterDialog = new FilterDialog(getActivity(), this);
        sortByDialog = new SortByDialog(getActivity(), this);
        viewModel.getColors();
        viewModel.getSizes();
        getData();
        errorMessage();
        pagesResponse();
        onViewClicked();
    }

    private void onViewClicked() {
        binding.sortBy.setOnClickListener(view -> showSortByDialog());
        binding.filterBy.setOnClickListener(view -> showFilterDialog());
        binding.result.setOnClickListener(view -> changeLayoutManger());
    }

    private void getData() {
        if (getArguments() != null) {
            category_id = getArguments().getInt("category_id");
            title = getArguments().getString("title");
            binding.title.setText(title);
            requestData();
//            if (viewModel.getProductsMutableLiveData().getValue() == null){
//                requestData();
//            }
        }
    }

    private void requestData() {
        helper.showLoading(requireActivity());
        request.setCategory_id(category_id);
        if (category_id == 0) {
            params.put("name", title);
        } else params.put("category_id", category_id);
        viewModel.getPages(params);
    }

    private void pagesResponse() {
        viewModel.getProductsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<ProductModel>>>() {
            @Override
            public void onChanged(BaseModel<List<ProductModel>> listBaseModel) {
                helper.dismissLoading();
                productsList = listBaseModel;
                initProductsRec();
            }
        });
    }

    private void initProductsRec() {
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.recycler.setAdapter(productsAdapter);
        productsAdapter.addData(productsList.getItem().getData());
        vertical = true;
    }

    private void initProductsHorizontalRec() {
        ProductsHorizontalAdapter productsAdapter = new ProductsHorizontalAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(productsAdapter);
        productsAdapter.addData(productsList.getItem().getData());
        vertical = false;
    }

    private void changeLayoutManger() {
        if (vertical) {
            binding.result.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_result_grid), null);
            initProductsHorizontalRec();
        } else {
            binding.result.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    ContextCompat.getDrawable(requireActivity(), R.drawable.ic_result), null);
            initProductsRec();
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

    private void showSortByDialog() {
        sortByDialog.show();
    }

    private void showFilterDialog() {
        filterDialog.show();
    }

    @Override
    public void sortBy(String sortBy) {
        sortByDialog.dismiss();
        params.put("sort_by", sortBy);
        helper.showLoading(requireActivity());
        viewModel.getPages(params);
    }

    @Override
    public void onViewResultClicked(Map<String, Object> params) {
        filterDialog.dismiss();
        helper.showLoading(requireActivity());
        viewModel.getPages(params);
    }
}