package com.macaria.app.ui.homeScreen.categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CategoriesFragmentBinding;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.ui.homeScreen.categories.adapters.CategoryBarAdapter;
import com.macaria.app.ui.homeScreen.home.homeView.adapters.HomeCategoryAdapter;
import com.macaria.app.ui.homeScreen.home.homeView.listeners.CategoriesListener;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment implements CategoriesListener {
    private CategoriesFragmentBinding binding ;

    @Inject
    MyHelper helper ;

    public CategoriesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = CategoriesFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        initCategoryRec();
    }

    private void initCategoryRec() {
        CategoryBarAdapter HomeCategoryAdapter = new CategoryBarAdapter(getActivity(), this);
        binding.categoryRec.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRec.setAdapter(HomeCategoryAdapter);
        HomeCategoryAdapter.addData(viewModel.getCategoriesList());
        initSliderRec(viewModel.getCategoriesList());
    }

    @Override
    public void onCategorySelected(int id) {

    }
}