package com.macaria.app.ui.authorization.createAccount.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.macaria.app.R;
import com.macaria.app.databinding.SplashFragmentBinding;
import com.macaria.app.databinding.VerifyMobileFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.createAccount.vm.CreateAccountViewModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.LoginRequest;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerifyMobileFragment extends Fragment {
    private VerifyMobileFragmentBinding binding;
    private CreateAccountViewModel viewModel;
    private boolean sendCode = false;
    private String mobile = "";

    @Inject
    MyHelper helper;

    public VerifyMobileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = VerifyMobileFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        if (getArguments() != null) this.mobile = getArguments().getString("mobile");
        binding.mobile.setText(mobile);
        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
        viewModel.clear();
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(), this::loginResponse);
        binding.sendCode.setOnClickListener(view -> reSendCode());
        submitFun();
        errorMessage();
        counter();
        sendCodeResponse();
    }

    private void submitFun() {
        binding.submit.setOnClickListener(view -> {
            String otp = binding.pinview.getValue();
            if (otp.length() < 4) {
                Toast.makeText(getActivity(), "please add a valid otp", Toast.LENGTH_SHORT).show();
            } else verifyRequest(otp);
        });
    }

    private void verifyRequest(String otp) {
        helper.showLoading(getActivity());
        LoginRequest request = new LoginRequest();
        request.setMobile(mobile);
        request.setOtp(otp);
        viewModel.verifyRequest(request);
    }

    private void loginResponse(BaseModel<AuthModel> loginModelBaseModel) {
        helper.dismissLoading();
        new AuthData(getActivity()).SaveUserData(loginModelBaseModel.getItem().getData());
        Navigation.findNavController(requireView()).navigate(R.id.action_verifyMobileFragment_to_homeScreenFragment);
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void counter() {
        sendCode = false;
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timer.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.timer.setText("00:00");
                sendCode = true;
            }
        }.start();
    }

    private void reSendCode(){
        if (sendCode){
            LoginRequest request = new LoginRequest();
            request.setMobile(mobile);
            helper.showLoading(getActivity());
            viewModel.sendCode(request);
        }else Toast.makeText(getActivity(), "please wait..", Toast.LENGTH_SHORT).show();
    }

    private void sendCodeResponse() {
        viewModel.sendCodeResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                counter();
            }
        });
    }
}