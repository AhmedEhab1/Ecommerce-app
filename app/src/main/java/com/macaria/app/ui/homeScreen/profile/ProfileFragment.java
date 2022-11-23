package com.macaria.app.ui.homeScreen.profile;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.MainActivity;
import com.macaria.app.R;
import com.macaria.app.databinding.ProfileFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.authorization.login.vm.LoginViewModel;
import com.macaria.app.ui.homeScreen.profile.vm.ProfileViewModel;
import com.macaria.app.ui.homeScreen.profile.webViews.WebViewModel;
import com.macaria.app.utilities.MyHelper;
import com.macaria.app.utilities.errorDialog.ErrorDialog;
import com.macaria.app.utilities.errorDialog.ErrorDialogListener;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment implements ErrorDialogListener {
    private ProfileFragmentBinding binding;
    private ProfileViewModel viewModel;
    private String terms, privacyPolicy;

    @Inject
    MyHelper helper;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setUserData();
        onViewClicked();
        getWebViewLinks();
    }

    private void setUserData() {
        UserModel userModel = new AuthData(getActivity()).getUserData().getUser();
        binding.userName.setText(userModel.getName());
        binding.userEmail.setText(userModel.getEmail());
        binding.profileImage.setClipToOutline(true);
        loadImage(getActivity(), userModel.getImage(), R.drawable.profile_holder, binding.profileImage);
    }

    private void onViewClicked() {
        binding.userInfoData.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_accountInfo));
        binding.savedAddresses.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_savedAddressesFragment));
        binding.logout.setOnClickListener(view -> showLogoutDialog());
    }

    private void termsConditions(){
        Bundle bundle = new Bundle();
        bundle.putString("link", terms);
        bundle.putString("title", getString(R.string.terms_conditions));
        binding.termsConditions.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_webViewFragment, bundle));
    }

    private void privacyPolicy(){
        Bundle bundle = new Bundle();
        bundle.putString("link", privacyPolicy);
        bundle.putString("title", getString(R.string.privacy_policy));
        binding.privacyPolicy.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_webViewFragment, bundle));
    }

    private void showLogoutDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.logout));
        bundle.putString("message", getString(R.string.logout_message));
        FragmentActivity activity = (FragmentActivity) (requireActivity());
        FragmentManager fm = activity.getSupportFragmentManager();
        ErrorDialog errorDialog = new ErrorDialog(this);
        errorDialog.setArguments(bundle);
        errorDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onConfirm() {
        helper.showLoading(requireActivity());
        viewModel.loginRequest();
        logoutResponse();
        errorMessage();
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void logoutResponse() {
        viewModel.getLogoutResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                new AuthData(getActivity()).logout();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void getWebViewLinks() {
        viewModel.webViewRequest();
        viewModel.getWebViewResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<WebViewModel>>() {
            @Override
            public void onChanged(BaseModel<WebViewModel> webViewModelBaseModel) {
                terms = webViewModelBaseModel.getItem().getData().getTerms();
                privacyPolicy = webViewModelBaseModel.getItem().getData().getPrivacyPolicy();
                privacyPolicy();
                termsConditions();
            }
        });
    }

}