package com.macaria.app.ui.authorization.createAccount.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.R;
import com.macaria.app.databinding.CreareAccountFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.createAccount.vm.CreateAccountViewModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.ui.authorization.login.vm.LoginViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;


public class CreateAccountFragment extends Fragment {
    private CreareAccountFragmentBinding binding;
    private CreateAccountViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = CreareAccountFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
        viewModel.clear();
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(), this::loginResponse);
        onViewClicked();
        errorMessage();
    }

    private void onViewClicked(){
        binding.login.setOnClickListener(view -> Navigation.findNavController(requireView()).popBackStack());
        binding.createAccount.setOnClickListener(view -> loginRequest());
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
            CreateAccountRequest request = new CreateAccountRequest();
            request.setMobile(mobile);
            request.setPassword(password);
            viewModel.loginRequest(request);
        }
    }

    private void loginResponse(BaseModel<AuthModel> loginModelBaseModel) {
        helper.dismissLoading();
       // Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_forgetPassword);
    }


    private void errorMessage(){
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity() , null , s);
            }
        });
    }
}