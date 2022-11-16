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
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.authorization.login.vm.LoginViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class CreateAccountFragment extends Fragment {
    private CreareAccountFragmentBinding binding;
    private CreateAccountViewModel viewModel;

    @Inject
    MyHelper helper;

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

    private void onViewClicked() {
        binding.login.setOnClickListener(view -> Navigation.findNavController(requireView()).popBackStack());
        binding.createAccount.setOnClickListener(view -> loginRequest());
    }

    private void loginRequest() {
        String firstName = binding.firstName.getText().toString();
        String lastName = binding.lastName.getText().toString();
        String mobile = binding.phoneNumber.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        showErrorText(firstName, lastName, email, mobile, password);
        isDataValid(firstName, lastName, email, mobile, password);
    }

    private void showErrorText(String firstName, String lastName, String email, String mobile, String password) {
        if (firstName.length() < 2) binding.firstName.setError(getString(R.string.add_valid_name));
        if (lastName.length() < 2) binding.lastName.setError(getString(R.string.add_valid_name));
        if (email.length() < 5) binding.email.setError(getString(R.string.add_valid_email));
        if (mobile.length() != 11)
            binding.phoneNumber.setError(getString(R.string.add_valid_mobile));
    }

    private void isDataValid(String firstName, String lastName, String email, String mobile, String password) {
        if (firstName.length() < 2) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_name), Toast.LENGTH_SHORT).show();
        } else if (lastName.length() < 2) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_name), Toast.LENGTH_SHORT).show();
        } else if (email.length() < 5) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_email), Toast.LENGTH_SHORT).show();
        } else if (mobile.length() != 11) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_mobile), Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_password), Toast.LENGTH_SHORT).show();
        } else {
            helper.showLoading(getActivity());
            CreateAccountRequest request = new CreateAccountRequest();
            request.setFirst_name(firstName);
            request.setLast_name(lastName);
            request.setEmail(email);
            request.setMobile(mobile);
            request.setPassword(password);
            viewModel.loginRequest(request);
        }
    }

    private void loginResponse(BaseModel<AuthModel> loginModelBaseModel) {
        helper.dismissLoading();
        if (loginModelBaseModel.getSuccess()) {
            UserModel userModel = loginModelBaseModel.getItem().getData().getUser() ;
            navigateToVerify(userModel.getMobile());
        }
    }

    private void navigateToVerify(String mobile) {
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        Navigation.findNavController(requireView()).navigate(R.id.action_creareAccountFragment_to_verifyMobileFragment, bundle);
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