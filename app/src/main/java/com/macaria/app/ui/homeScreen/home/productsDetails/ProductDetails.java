package com.macaria.app.ui.homeScreen.home.productsDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FavoriteFragmentBinding;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ColorModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.products.models.SuggestedProducts;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.ColorAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.SizeAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.SliderAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.SuggestedProductsAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.ColorListener;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;
import com.macaria.app.utilities.MyHelper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetails extends Fragment implements ProductsListener, SizeListener, ColorListener {
    private ProductDetailsFragmentBinding binding ;
    private ProductModel model ;

    @Inject
    MyHelper helper ;

    public ProductDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null){
            binding = ProductDetailsFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init(){
        binding.icBack.setOnClickListener(view -> back());
        getProductsData();
    }

    private void setViewData(){
        setInfoData();
        setReviewData();
        initProductRec(model.getSuggestedProducts().getData());
        initSizeRec(model.getSizes());
        initSliderRec(model.getImages());
        initColorRec(model.getColors());
    }

    private void getProductsData(){
        if (getArguments() != null){
            model = (ProductModel) getArguments().getSerializable("ProductModel");
            setViewData();
        }else {
            helper.showLoading(requireActivity());
        }
    }

    private void setInfoData(){
        binding.title.setText(model.getName());
        binding.description.setText(model.getDescription());
        binding.price.setText(model.getPrice().concat(" ").concat(getString(R.string.egp)));
        binding.oldPrice.setText(model.getOldPrice().concat(" ").concat(getString(R.string.egp)));
        binding.productRate.setRating(model.getRate());
        binding.totalReviews.setText("( ".concat(String.valueOf(model.getReviewCount())).concat(" ").concat(getString(R.string.reviewsSmall)).concat(" )"));
        if (model.getFav())binding.favIcon.setImageResource(R.drawable.ic_favorite_active);
    }

    private void setReviewData(){
        try {
            if (model.getReviews().size() == 0){
                binding.allReviewsLayout.setVisibility(View.GONE);
                binding.allReviewsItem.getRoot().setVisibility(View.GONE);
            }else {
                binding.allReviewsItem.title.setText(model.getReviews().get(0).getTitle());
                binding.allReviewsItem.description.setText(model.getReviews().get(0).getReview());
                binding.allReviewsItem.date.setText(model.getReviews().get(0).getCreatedAt());
                binding.allReviewsItem.byUser.setText(getString(R.string.by).concat(model.getReviews().get(0).getCreatedAt()));
                binding.allReviewsItem.rate.setRating(model.getReviews().get(0).getRate());
            }
        }catch (Exception e){
            Log.e("crash", "setReviewData: ",e );
        }
    }

    private void initProductRec(List<SuggestedProducts> listBaseModel) {
        SuggestedProductsAdapter addressAdapter = new SuggestedProductsAdapter(getActivity(), this);
        binding.productsRec.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.productsRec.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel);
    }

    private void initSizeRec(List<SizeModel> model) {
        SizeAdapter addressAdapter = new SizeAdapter(getActivity(), this);
        binding.sizeRec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.sizeRec.setAdapter(addressAdapter);
        addressAdapter.addData(model);
    }

    private void initColorRec(List<ColorModel> model) {
        ColorAdapter addressAdapter = new ColorAdapter(getActivity(), this);
        binding.colorRec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.colorRec.setAdapter(addressAdapter);
        addressAdapter.addData(model);
    }

    private void initSliderRec(List<String> model) {
        SliderView sliderView = binding.imageSlider;
        SliderAdapter adapter = new SliderAdapter(requireActivity());
        sliderView.setSliderAdapter(adapter);
        adapter.renewItems(model);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void back() {
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void onProductClick(ProductModel model) {

    }

    @Override
    public void onFavoriteClick(int id) {

    }

    @Override
    public void onSizeSelected(int id) {

    }

    @Override
    public void onColorSelected(int id) {

    }
}