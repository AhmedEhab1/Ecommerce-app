package com.macaria.app.ui.authorization.login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.databinding.LoginFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.R;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.authorization.login.vm.LoginViewModel;
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
        if (binding == null){
            binding = LoginFragmentBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        }
        init();

        return binding.getRoot();
    }

    private void init() {
        viewModel.clear();
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(), this::loginResponse);
        onViewClicked();
        errorMessage();
    }

    private void onViewClicked(){
        binding.loginBtn.setOnClickListener(view -> loginRequest());
        binding.forgetPassword.setOnClickListener(view -> forgetPassword());
        binding.createAccount.setOnClickListener(view -> createAccount());
    }

    private void loginRequest() {
        String mobile = binding.phoneNumber.getText().toString();
        String password = binding.password.getText().toString();
        if (mobile.length() != 11) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_mobile), Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_password), Toast.LENGTH_SHORT).show();
        } else {
            helper.showLoading(getActivity());
            LoginRequest request = new LoginRequest();
            request.setMobile(mobile);
            request.setPassword(password);
            viewModel.loginRequest(request);
        }
    }

    private void loginResponse(BaseModel<AuthModel> loginModelBaseModel) {
        helper.dismissLoading();
        if (loginModelBaseModel.getSuccess()){
            new AuthData(getActivity()).SaveUserData(loginModelBaseModel.getItem().getData());
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeScreenFragment);
        }
    }

    private void errorMessage(){
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity() , null , s);
            }
        });
    }
    private void forgetPassword(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_forgetPassword);
    }

    private void createAccount(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_creareAccountFragment);
    }
}