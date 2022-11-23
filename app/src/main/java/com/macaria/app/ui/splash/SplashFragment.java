package com.macaria.app.ui.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.SplashFragmentBinding;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.AuthModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends Fragment {
    private SplashFragmentBinding binding;

    @Inject
    Handler handler;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SplashFragmentBinding.inflate(inflater, container, false);
        handler.postDelayed(this::getAuthToken, 1800);
        return binding.getRoot();
    }

    private void getAuthToken(){
        AuthModel authModel = new AuthData(getActivity()).getUserData();
        if (authModel.getToken().equals("")){
            navigateToLogin();
        }else navigateToHome();
    }

    private void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }

    private void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeScreenFragment);
    }
}