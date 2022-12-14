package com.macaria.app.ui.homeScreen.categories.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.CategoriesFragmentBinding;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.categories.adapters.CategoryBarAdapter;
import com.macaria.app.ui.homeScreen.categories.adapters.SubCategoryAdapter;
import com.macaria.app.ui.homeScreen.categories.adapters.SubItemAdapter;
import com.macaria.app.ui.homeScreen.categories.adapters.SubItemsListener;
import com.macaria.app.ui.homeScreen.categories.vm.CategoriesViewModel;
import com.macaria.app.ui.homeScreen.home.homeView.adapters.HomeCategoryAdapter;
import com.macaria.app.ui.homeScreen.home.homeView.listeners.CategoriesListener;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.vm.HomeViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment implements CategoriesListener, SubItemsListener {
    private CategoriesFragmentBinding binding ;
    private CategoriesViewModel viewModel ;

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
        viewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        initCategoryRec();
        subCategoryResponse();
        subItemResponse();
        errorMessage();
    }

    private void initCategoryRec() {
        CategoryBarAdapter adapter = new CategoryBarAdapter(getActivity(), this);
        binding.categoryRec.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRec.setAdapter(adapter);
        adapter.addData(viewModel.getCategoriesList());
        viewModel.getSubCategory(viewModel.getCategoriesList().get(0).getId());
    }

    @Override
    public void onCategorySelected(int id) {
        viewModel.getSubCategory(id);
    }

    private void subCategoryResponse(){
        viewModel.getSubCategoryMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<CategoriesModel>>>() {
            @Override
            public void onChanged(BaseModel<List<CategoriesModel>> listBaseModel) {
                initSubCategoryRec(listBaseModel.getItem().getData());
            }
        });
    }

    private void initSubCategoryRec(List<CategoriesModel> list) {
        SubCategoryAdapter adapter = new SubCategoryAdapter(getActivity(), this);
        binding.subCategoryRec.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        binding.subCategoryRec.setAdapter(adapter);
        adapter.addData(list);
        viewModel.getSubItem(list.get(0).getId());

    }

    private void initSubItemRec(List<CategoriesModel> list) {
        SubItemAdapter adapter = new SubItemAdapter(getActivity(), this);
        binding.subItemRec.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        binding.subItemRec.setAdapter(adapter);
        adapter.addData(list);
    }

    @Override
    public void onSubCategoryClicked(int id) {
        viewModel.getSubItem(id);
    }

    @Override
    public void onSubItemClicked(String title,int id) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("category_id", id);
        Navigation.findNavController(requireView()).navigate(R.id.action_categoriesFragment_to_filterFragment, bundle);
    }

    private void subItemResponse(){
        viewModel.getSubItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<CategoriesModel>>>() {
            @Override
            public void onChanged(BaseModel<List<CategoriesModel>> listBaseModel) {
                initSubItemRec(listBaseModel.getItem().getData());
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
}