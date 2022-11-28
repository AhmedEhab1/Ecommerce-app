package com.macaria.app.ui.homeScreen.profile.orderHistory.fragments;

import static com.macaria.app.utilities.ImageHelper.loadImage;
import static com.macaria.app.utilities.InputValidatorHelper.isEmptyString;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.AddReviewFragmentBinding;
import com.macaria.app.databinding.OrderDetailsFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.AddReviewRequest;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderDetailsModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.AddReviewViewModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.OrderHistoryViewModel;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddReviewFragment extends Fragment {
    private AddReviewFragmentBinding binding;
    private OrderDetailsModel model;
    private AddReviewViewModel viewModel ;

    @Inject
    MyHelper helper;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddReviewFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(AddReviewViewModel.class);
        getModel();
        setViewData();
        errorMessage();
        binding.submit.setOnClickListener(view -> addReview());
    }

    private void getModel() {
        if (getArguments() != null) {
            model = (OrderDetailsModel) getArguments().getSerializable("model");
        }
    }

    private void setViewData(){
        try {
            binding.image.setClipToOutline(true);
            binding.productDescription.setText(model.getProduct_description());
            binding.price.setText(model.getName().concat(" ").concat(getString(R.string.egp)));
            binding.title.setText(model.getName());
            binding.percentage.setText(model.getPercentage());
            loadImage(requireActivity(), model.getImage(), R.drawable.profile_holder, binding.image);
            if (model.isIs_fav()){binding.icFav.setImageResource(R.drawable.ic_favorite_active);}
        }catch (Exception e){
            Log.e("crash", "setViewData: ", e);
        }
    }

    private void addReview(){
        AddReviewRequest request = new AddReviewRequest();
        request.setRate(String.valueOf((int)binding.ratingBar.getRating()));
        request.setReview( binding.leaveComment.getText().toString());
        request.setTitle(binding.commentTitle.getText().toString());
        request.setProduct_id(String.valueOf(model.getId()));
        isDataValid(request);
    }

    private void isDataValid(AddReviewRequest request){
        if (isEmptyString(request.getReview())){
            helper.showToast(requireActivity(), "please add valid review");
        }else  if (isEmptyString(request.getTitle())){
            helper.showToast(requireActivity(), "please add valid title");
        }else addReviewRequest(request);
    }

    private void addReviewRequest(AddReviewRequest request){
        helper.showLoading(requireActivity());
        viewModel.addReview(request);
        addReviewResponse();
    }

    private void addReviewResponse(){
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                if (model.getSuccess()){
                    helper.showToast(requireActivity() , model.getMessage());
                    back();
                }
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