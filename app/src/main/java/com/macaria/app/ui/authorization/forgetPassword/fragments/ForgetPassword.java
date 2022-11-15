package com.macaria.app.ui.authorization.forgetPassword.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.R;
import com.macaria.app.databinding.ForgetPasswordFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.vm.ForgetPasswordViewModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgetPassword extends Fragment {
    private ForgetPasswordFragmentBinding binding ;
    private ForgetPasswordViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public ForgetPassword() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ForgetPasswordFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        binding.send.setOnClickListener(view -> forgetPasswordRequest());
        loginResponse();
        errorMessage();
    }

    private void forgetPasswordRequest() {
        String mobile = binding.phoneNumber.getText().toString();
        if (mobile.length() != 11) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_mobile), Toast.LENGTH_SHORT).show();
        }  else {
            helper.showLoading(getActivity());
            LoginRequest request = new LoginRequest();
            request.setMobile(mobile);
            viewModel.forgetPasswordRequest(request);
        }
    }

    private void loginResponse() {
        viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                Log.d("TAG", "onChanged: ");
              //  Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_forgetPassword);

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