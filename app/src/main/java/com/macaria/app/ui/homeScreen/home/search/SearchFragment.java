package com.macaria.app.ui.homeScreen.home.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.databinding.SearchFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.vm.ProductDetailsViewModel;
import com.macaria.app.ui.homeScreen.home.search.vm.SearchViewModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters.AllAddressAdapter;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements SearchListener{
    private SearchFragmentBinding binding ;
    private SearchViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = SearchFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.getCategoriesTrend();
        getCategoryTrend();
        errorMessage();
    }

    private void getCategoryTrend(){
        viewModel.getTrendModel().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<CategoriesModel>>>() {
            @Override
            public void onChanged(BaseModel<List<CategoriesModel>> listBaseModel) {
                initRec(listBaseModel.getItem().getData());
            }
        });
    }


    private void initRec(List<CategoriesModel> listBaseModel) {
        TrendAdapter addressAdapter = new TrendAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel);
    }

    private void errorMessage(){
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity() , null , s);
            }
        });
    }

    @Override
    public void onCategoryTrendClicked(int id) {

    }
}