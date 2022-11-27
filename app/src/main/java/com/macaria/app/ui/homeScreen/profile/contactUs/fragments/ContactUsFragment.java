package com.macaria.app.ui.homeScreen.profile.contactUs.fragments;

import static com.macaria.app.data.Constants.MOBILE_LENGTH;
import static com.macaria.app.utilities.InputValidatorHelper.isEmptyString;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.ContactUsFragmentBinding;
import com.macaria.app.databinding.SplashFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.profile.accountInfo.vm.AccountInfoViewModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactModel;
import com.macaria.app.ui.homeScreen.profile.contactUs.models.ContactUsRequest;
import com.macaria.app.ui.homeScreen.profile.contactUs.vm.ContactUsViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContactUsFragment extends Fragment {
    private ContactUsFragmentBinding binding;
    private ContactUsViewModel viewModel;
    private ContactUsRequest request = new ContactUsRequest();

    @Inject
    MyHelper helper;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ContactUsFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        requestContactData();
        binding.submit.setOnClickListener(view -> onSubmitClicked());
        errorMessage();
    }

    private void requestContactData() {
        viewModel = new ViewModelProvider(this).get(ContactUsViewModel.class);
        helper.showLoading(requireActivity());
        viewModel.requestContact();
        contactResponse();
    }

    private void contactResponse() {
        viewModel.getContactResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<ContactModel>>() {
            @Override
            public void onChanged(BaseModel<ContactModel> contactModelBaseModel) {
                helper.dismissLoading();
                binding.email.setText(contactModelBaseModel.getItem().getData().getEmail());
                binding.phone.setText(contactModelBaseModel.getItem().getData().getPhone());
            }
        });
    }

    private void onSubmitClicked() {
        request.setName(binding.yourName.getText().toString());
        request.setEmail(binding.emailAddress.getText().toString());
        request.setMobile(binding.phoneNumber.getText().toString());
        request.setMassage(binding.message.getText().toString());
        checkIfDataValid();
    }

    private void checkIfDataValid() {
        if (isEmptyString(request.getName())) {
            helper.showToast(requireActivity(), "please add valid name");
        } else if (isEmptyString(request.getMassage())) {
            helper.showToast(requireActivity(), "please valid message");
        } else if (isEmptyString(request.getEmail())) {
            helper.showToast(requireActivity(), "please valid email");
        } else if (request.getMobile().length() != MOBILE_LENGTH) {
            helper.showToast(requireActivity(), "please valid Phone Number");
        } else {
            requestContactUS();
        }
    }

    private void requestContactUS() {
        helper.showLoading(requireActivity());
        viewModel.contactUs(request);
        contactUsResponse();
    }

    private void contactUsResponse() {
        viewModel.getContactUs().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                helper.showToast(requireActivity(), getString(R.string.message_send));
                if (model.getSuccess()) back();
            }
        });
    }

    public void back() {
        Navigation.findNavController(requireView()).popBackStack();
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