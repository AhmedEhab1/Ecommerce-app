package com.macaria.app.ui.authorization.forgetPassword.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.R;
import com.macaria.app.databinding.ForgetPasswordFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.forgetPassword.vm.ForgetPasswordViewModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgetPassword extends Fragment implements ResetPasswordListener{
    private ForgetPasswordFragmentBinding binding ;
    private ForgetPasswordViewModel viewModel ;
    private String mobile , userId ;
    ResetPasswordDialog resetPasswordDialog ;
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
        showResetPasswordDialog();
        confirmCodeResponse();
    }

    private void forgetPasswordRequest() {
        mobile = binding.phoneNumber.getText().toString();
        if (mobile.length() != 11)
            Toast.makeText(getActivity(), getString(R.string.add_valid_mobile), Toast.LENGTH_SHORT).show();
          else sendCode();
    }

    private void sendCode(){
        helper.showLoading(getActivity());
        LoginRequest request = new LoginRequest();
        request.setMobile(mobile);
        viewModel.forgetPasswordRequest(request);
    }

    private void loginResponse() {
        viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<ForgetPasswordModel>>() {
            @Override
            public void onChanged(BaseModel<ForgetPasswordModel> forgetPasswordModelBaseModel) {
                helper.dismissLoading();
                showResetPasswordDialog();
                userId = forgetPasswordModelBaseModel.getItem().getData().getUser_id();
            }
        });
    }

    private void confirmCodeResponse() {
        viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<ForgetPasswordModel>>() {
            @Override
            public void onChanged(BaseModel<ForgetPasswordModel> forgetPasswordModelBaseModel) {
              // navigate
                Navigation.findNavController(requireView()).navigate(R.id.action_forgetPassword_to_createNewPasswordFragment);
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

    private void showResetPasswordDialog(){
        resetPasswordDialog = new ResetPasswordDialog(getActivity() , this, mobile);
        resetPasswordDialog.show();
    }

    @Override
    public void onOtpEntered(String otp) {
        resetPasswordDialog.dismiss();
        helper.showLoading(getActivity());
        ForgetPasswordModel request = new ForgetPasswordModel();
        request.setOtp(otp);
        request.setUser_id(userId);
        viewModel.confirmCode(request);
    }

    @Override
    public void onSendCodeClicked() {
        sendCode();
    }
}