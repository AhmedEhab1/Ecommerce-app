package com.macaria.app.ui.homeScreen.profile.accountInfo;

import static com.macaria.app.utilities.ImageHelper.getMultipartBodyImage;
import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.AccountInfoFragmentBinding;
import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.authorization.login.model.UserModel;
import com.macaria.app.utilities.PermissionUtil;

import okhttp3.MultipartBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;


public class AccountInfo extends Fragment {
    private AccountInfoFragmentBinding binding ;
    private EasyImage easyImage;
    private MultipartBody.Part imagePart ;


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
        setUserData();
        onViewClicked();
        requestEasyImage();
    }

    private void onViewClicked(){
        binding.changePassword.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_accountInfo_to_changePasswordFragment));
        binding.changeImage.setOnClickListener(view -> askForPermission());
    }

    private void setUserData() {
        UserModel userModel = new AuthData(getActivity()).getUserData().getUser();
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
        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
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


}