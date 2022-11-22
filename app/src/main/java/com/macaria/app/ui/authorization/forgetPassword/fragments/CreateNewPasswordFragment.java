package com.macaria.app.ui.authorization.forgetPassword.fragments;

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
import com.macaria.app.databinding.CreateNewPasswordFragmentBinding;
import com.macaria.app.databinding.ForgetPasswordFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.forgetPassword.vm.ForgetPasswordViewModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateNewPasswordFragment extends Fragment {
    private CreateNewPasswordFragmentBinding binding;
    private int user_id ;
    private ForgetPasswordViewModel viewModel ;

    @Inject
    MyHelper helper;

    public CreateNewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CreateNewPasswordFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        if (getArguments() != null) this.user_id = getArguments().getInt("user_id");
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        getResponse();
        binding.submit.setOnClickListener(view -> changePassword());
        errorMessage();
    }

    private void changePassword() {
        String password = binding.password.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();
        if (password.length() < 5 || confirmPassword.length() < 5) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_password), Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
        } else {
            helper.showLoading(getActivity());
            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setUser_id(user_id);
            request.setPassword(password);
            viewModel.updatePassword(request);
        }
    }

    private void getResponse() {
        viewModel.getUpdatePasswordResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel forgetPasswordModelBaseModel) {
                helper.dismissLoading();
                Navigation.findNavController(requireView()).navigate(R.id.action_createNewPasswordFragment_to_homeScreenFragment);
            }
        });
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