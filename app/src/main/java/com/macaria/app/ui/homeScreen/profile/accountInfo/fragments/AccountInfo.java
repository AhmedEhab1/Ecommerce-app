package com.macaria.app.ui.homeScreen.profile.accountInfo.fragments;

import static com.macaria.app.utilities.ImageHelper.getMultipartBodyImage;
import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.macaria.app.databinding.AccountInfoFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.createAccount.CreateAccountRequest;
import com.macaria.app.ui.authorization.createAccount.vm.CreateAccountViewModel;
import com.macaria.app.ui.authorization.forgetPassword.model.ForgetPasswordModel;
import com.macaria.app.ui.authorization.login.model.AuthModel;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.ui.homeScreen.profile.accountInfo.vm.AccountInfoViewModel;
import com.macaria.app.utilities.MyHelper;
import com.macaria.app.utilities.PermissionUtil;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MultipartBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

@AndroidEntryPoint
public class AccountInfo extends Fragment {
    private AccountInfoFragmentBinding binding ;
    private EasyImage easyImage;
    private MultipartBody.Part imagePart ;
    private AccountInfoViewModel viewModel ;
    private UserModel userModel ;

    @Inject
    MyHelper helper;

    public AccountInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AccountInfoFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(AccountInfoViewModel.class);
        viewModel.clear();
        setUserData();
        onViewClicked();
        requestEasyImage();
        changeDataResponse();
        errorMessage();
    }

    private void onViewClicked(){
        saveChanges();
        binding.changePassword.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_accountInfo_to_changePasswordFragment));
        binding.changeImage.setOnClickListener(view -> askForPermission());
    }

    private void saveChanges(){
        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstName.getText().toString();
                String lastName = binding.lastName.getText().toString();
                String email = binding.userEmail.getText().toString();
                isDataValid(firstName, lastName, email);
            }
        });
    }

    private void setUserData() {
        userModel = new AuthData(getActivity()).getUserData().getUser();
        binding.firstName.setText(userModel.getFirstName());
        binding.lastName.setText(userModel.getLastName());
        binding.userEmail.setText(userModel.getEmail());
        binding.profileImage.setClipToOutline(true);
        loadImage(getActivity(), userModel.getImage(), R.drawable.profile_holder, binding.profileImage);
    }

    private void requestEasyImage() {
        easyImage = new EasyImage.Builder(requireActivity())
                .setCopyImagesToPublicGalleryFolder(true)
                .setFolderName("macaria")
                .allowMultiple(false)
                .build();
    }


    private void askForPermission() {
        if (getActivity() != null && isAdded())
            if (!PermissionUtil.isPermissionGranted(getActivity())) {
                PermissionUtil.askForFileAndCameraPermission(getActivity());
            } else pickImageFromGallery();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        pickImageFromGallery();
    }

    private void pickImageFromGallery() {
        easyImage.openGallery(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, requireActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {

            }

            @Override
            public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mediaFiles[0].getFile().getAbsolutePath());
                    binding.profileImage.setImageBitmap(myBitmap);
                    imagePart = getMultipartBodyImage(mediaFiles[0].getFile(), "image");
            }

            @Override
            public void onCanceled(@NonNull MediaSource mediaSource) {

            }
        });
    }

    private void isDataValid(String firstName, String lastName, String email) {
        if (firstName.length() < 2) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_name), Toast.LENGTH_SHORT).show();
        } else if (lastName.length() < 2) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_name), Toast.LENGTH_SHORT).show();
        } else if (email.length() < 5) {
            Toast.makeText(getActivity(), getString(R.string.add_valid_email), Toast.LENGTH_SHORT).show();
        }else {
            helper.showLoading(getActivity());
            CreateAccountRequest request = new CreateAccountRequest();
            request.setFirst_name(firstName);
            request.setLast_name(lastName);
            request.setEmail(email);
            request.setMobile(userModel.getEmail());
            if (imagePart == null)viewModel.changeAccountInfoRequest(request);
            else viewModel.changeAccountInfoRequest(request, imagePart);
        }
    }

    private void changeDataResponse(){
            viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<UserModel>>() {
                @Override
                public void onChanged(BaseModel<UserModel> model) {
                    helper.dismissLoading();
                    new AuthData(getActivity()).saveUserData(model.getItem().getData());
                    back();
                }
            });
    }

    public void back(){
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