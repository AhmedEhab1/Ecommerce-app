package com.macaria.app.ui.homeScreen.home.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
public class SearchFragment extends Fragment implements SearchListener {
    private SearchFragmentBinding binding;
    private SearchViewModel viewModel;

    @Inject
    MyHelper helper;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = SearchFragmentBinding.inflate(inflater, container, false);
            init();

        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.getCategoriesTrend();
        getCategoryTrend();
        errorMessage();
        onTextChange();
        onCancelClicked();
        onSearchClicked();
    }

    private void getCategoryTrend() {
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

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    @Override
    public void onCategoryTrendClicked(String title, int id) {
        openFilter(title, id);
    }

    private void openFilter(String title, int id){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("category_id", id);
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_filterFragment, bundle);
    }

    private void onTextChange(){
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1){
                    binding.cancel.setVisibility(View.VISIBLE);
                }else binding.cancel.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void onCancelClicked(){
        binding.cancel.setOnClickListener(view -> {
            binding.search.setText("");
        });
    }

    private void onSearchClicked(){
        binding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (binding.search.getText().toString().length() >1){
                        openFilter(binding.search.getText().toString(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}