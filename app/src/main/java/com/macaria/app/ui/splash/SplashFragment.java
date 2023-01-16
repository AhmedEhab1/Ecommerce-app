package com.macaria.app.ui.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.data.HomeData;
import com.macaria.app.databinding.SplashFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.homeView.vm.HomeViewModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.vm.ProductDetailsViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {
    private SplashFragmentBinding binding;
    private HomeViewModel viewModel;

    @Inject
    Handler handler;

    @Inject
    MyHelper helper ;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SplashFragmentBinding.inflate(inflater, container, false);
      //  handler.postDelayed(this::getAuthToken, 1800);
        init();
        return binding.getRoot();
    }

    private void getAuthToken(){
        AuthModel authModel = new AuthData(getActivity()).getUserData();
        if (authModel.getToken().equals("")){
            navigateToLogin();
        }else navigateToHome();
    }

    private void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeScreenFragment);
    }

    private void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeScreenFragment);
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getCategories();
        errorMessage();
        categoryResponse();
        homeResponse();
    }


    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void categoryResponse(){
        viewModel.getCategoryMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<CategoriesModel>>>() {
            @Override
            public void onChanged(BaseModel<List<CategoriesModel>> listBaseModel) {
                viewModel.getHome(listBaseModel.getItem().getData().get(0).getId());
                viewModel.setCategoryList(listBaseModel.getItem().getData());
            }
        });
    }

    private void homeResponse(){
        viewModel.getHomeModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<HomeModel>>() {
            @Override
            public void onChanged(BaseModel<HomeModel> homeModelBaseModel) {
                getAuthToken();
            }
        });
    }
}