package com.macaria.app.ui.homeScreen.home.allReviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.AllReviewsFragmentBinding;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.ui.homeScreen.home.allReviews.adapter.ReviewsAdapter;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.ReviewModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryAdapter;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllReviewsFragment extends Fragment {
    private AllReviewsFragmentBinding binding ;

    @Inject
    MyHelper helper ;

    public AllReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = AllReviewsFragmentBinding.inflate(inflater, container, false);
        }
        init();
        return binding.getRoot();
    }

    private void init(){
        getModel();
    }

    private void getModel() {
        if (getArguments() != null){
            ProductModel model = (ProductModel) getArguments().getSerializable("model");
            initReviewsRec(model.getReviews());
        }
    }
    private void initReviewsRec(List<ReviewModel> models){
        ReviewsAdapter addressAdapter = new ReviewsAdapter(getActivity());
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(addressAdapter);
        addressAdapter.addData(models);
    }
}