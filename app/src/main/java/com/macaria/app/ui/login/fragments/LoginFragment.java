package com.macaria.app.ui.login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.BaseModel;
import com.macaria.app.R;
import com.macaria.app.databinding.LoginFragmentBinding;
import com.macaria.app.ui.login.model.LoginModel;
import com.macaria.app.ui.login.model.LoginRequest;
import com.macaria.app.ui.login.vm.LoginViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;
    private LoginViewModel viewModel;

    @Inject
    MyHelper helper ;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.loginBtn.setOnClickListener(view -> loginRequest());
        loginResponse();
        helper.showErrorDialog(getActivity() , null , "test error dialog");
    }

    private void loginRequest() {
        String mobile = binding.phoneNumber.getText().toString();
        String password = binding.password.getText().toString();
        if (mobile.length() != 11) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_mobile), Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_password), Toast.LENGTH_SHORT).show();
        } else {
            LoginRequest request = new LoginRequest();
            request.setMobile(mobile);
            request.setPassword(password);
            viewModel.loginRequest(request);
        }
    }

    private void loginResponse() {
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<LoginModel>>() {
            @Override
            public void onChanged(BaseModel<LoginModel> loginModelBaseModel) {
                Toast.makeText(getActivity(), loginModelBaseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}