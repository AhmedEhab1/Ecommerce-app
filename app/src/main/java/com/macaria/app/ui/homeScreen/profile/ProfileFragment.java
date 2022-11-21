package com.macaria.app.ui.homeScreen.profile;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.ProfileFragmentBinding;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.UserModel;


public class ProfileFragment extends Fragment {
    ProfileFragmentBinding binding;

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
        setUserData();
        onViewClicked();
    }

    private void setUserData() {
        UserModel userModel = new AuthData(getActivity()).getUserData().getUser();
        binding.userName.setText(userModel.getName());
        binding.userEmail.setText(userModel.getEmail());
        binding.profileImage.setClipToOutline(true);
        loadImage(getActivity(), userModel.getImage(), R.drawable.profile_holder, binding.profileImage);
    }

    private void onViewClicked() {
        binding.accountInfo.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_accountInfo));
    }
}