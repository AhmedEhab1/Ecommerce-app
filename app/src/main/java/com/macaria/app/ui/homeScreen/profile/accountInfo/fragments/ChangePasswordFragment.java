package com.macaria.app.ui.homeScreen.profile.accountInfo.fragments;

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
import com.macaria.app.databinding.ChangePasswordFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.homeScreen.profile.accountInfo.vm.AccountInfoViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordFragment extends Fragment {
    private ChangePasswordFragmentBinding binding;
    private AccountInfoViewModel viewModel ;

    @Inject
    MyHelper helper ;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ChangePasswordFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(AccountInfoViewModel.class);
        viewModel.clear();
        getResponse();
        errorMessage();
        binding.saveChanges.setOnClickListener(view -> changePassword());
    }
    private void changePassword() {
        String currentPassword = binding.currentPassword.getText().toString();
        String password = binding.password.getText().toString();
        String confirmPassword = binding.confirmPassword.getText().toString();
        if (password.length() < 5 || confirmPassword.length() < 5) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_password), Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
        } else {
            helper.showLoading(getActivity());
            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setPassword(currentPassword);
            request.setNew_password(confirmPassword);
            viewModel.updatePassword(request);
        }
    }

    private void getResponse() {
        viewModel.getUpdatePasswordResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel forgetPasswordModelBaseModel) {
                helper.dismissLoading();
                back();
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

    public void back(){
        Navigation.findNavController(requireView()).popBackStack();
    }
}
