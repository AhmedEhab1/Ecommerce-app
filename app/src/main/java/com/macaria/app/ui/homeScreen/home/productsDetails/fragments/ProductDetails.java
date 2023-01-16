package com.macaria.app.ui.homeScreen.home.productsDetails.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
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
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SuggestedProductsListener;
import com.macaria.app.ui.homeScreen.home.productsDetails.sizeChart.SizeChartDialog;
import com.macaria.app.ui.homeScreen.home.productsDetails.vm.ProductDetailsViewModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.OrderHistoryViewModel;
import com.macaria.app.utilities.MyHelper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetails extends Fragment implements SuggestedProductsListener, SizeListener, ColorListener
        , Serializable {
    private ProductDetailsFragmentBinding binding;
    private ProductModel model;
    private ProductDetailsViewModel viewModel;
    private int size_id = 0, color_id = 0;

    @Inject
    MyHelper helper;

    @Inject
    SetFavoriteRequest favoriteRequest;

    public ProductDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = ProductDetailsFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        onViewClicked();
        getProductsData();
        getFavoriteResponse();
        errorMessage();
        productQty();
        addToCart();
    }

    private void onViewClicked() {
        binding.icBack.setOnClickListener(view -> back());
        binding.allReviews.setOnClickListener(view -> navigateToAllReviews());
        binding.favBtn.setOnClickListener(view -> setProductFavorite());
        binding.sizeGuide.setOnClickListener(view -> showSizeChartDialog());
        binding.cart.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.action_global_cartFragment));

    }

    private void setViewData() {
        setInfoData();
        setReviewData();
        initProductRec(model.getSuggestedProducts().getData());
        initSizeRec(model.getSizes());
        initSliderRec(model.getImages());
        initColorRec(model.getColors());
    }

    private void getProductsData() {
        if (getArguments() != null) {
            if (getArguments().getString("type").equals("model")) {
                model = (ProductModel) getArguments().getSerializable("ProductModel");
                setViewData();
            } else {
                int id = getArguments().getInt("id");
                helper.showLoading(requireActivity());
                viewModel.getProductsDetails(id);
                getProductsResponse();
            }
        }
    }

    private void setInfoData() {
        binding.title.setText(model.getName());
        binding.description.setText(model.getDescription());
        binding.price.setText(model.getPrice().concat(" ").concat(getString(R.string.egp)));
        if (!model.getOldPrice().equals(""))
            binding.oldPrice.setText(model.getOldPrice().concat(" ").concat(getString(R.string.egp)));
        binding.productRate.setRating(model.getRate());
        binding.totalReviews.setText("( ".concat(String.valueOf(model.getReviewCount())).concat(" ").concat(getString(R.string.reviewsSmall)).concat(" )"));
        if (model.getFav()) binding.favIcon.setImageResource(R.drawable.ic_favorite_active);
    }

    private void setReviewData() {
        try {
            if (model.getReviews().size() == 0) {
                binding.allReviewsLayout.setVisibility(View.GONE);
                binding.allReviewsItem.getRoot().setVisibility(View.GONE);
            } else {
                binding.allReviewsItem.title.setText(model.getReviews().get(0).getTitle());
                binding.allReviewsItem.description.setText(model.getReviews().get(0).getReview());
                binding.allReviewsItem.date.setText(model.getReviews().get(0).getCreatedAt());
                binding.allReviewsItem.byUser.setText(getString(R.string.by).concat(model.getReviews().get(0).getCreatedAt()));
                binding.allReviewsItem.rate.setRating(model.getReviews().get(0).getRate());
            }
        } catch (Exception e) {
            Log.e("crash", "setReviewData: ", e);
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
        size_id = model.get(0).getId();
    }

    private void initColorRec(List<ColorModel> model) {
        ColorAdapter addressAdapter = new ColorAdapter(getActivity(), this);
        binding.colorRec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.colorRec.setAdapter(addressAdapter);
        addressAdapter.addData(model);
        color_id = model.get(0).getId();
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
    public void onSuggestedProductsClicked(int id) {
        Bundle bundle = new Bundle();
        bundle.putString("type", "id");
        bundle.putInt("id", id);
        Navigation.findNavController(requireView()).navigate(R.id.action_product_details_self, bundle);
    }

    @Override
    public void onFavoriteClick(int id) {
        favoriteRequest.setProduct_id(id);
        helper.showLoading(requireActivity());
        viewModel.setFavorite(favoriteRequest);
    }

    @Override
    public void onSizeSelected(int id) {
        size_id = id;
    }

    @Override
    public void onColorSelected(int id) {
        color_id = id;
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void getProductsResponse() {
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<ProductModel>>() {
            @Override
            public void onChanged(BaseModel<ProductModel> productModelBaseModel) {
                helper.dismissLoading();
                model = productModelBaseModel.getItem().getData();
                setViewData();
            }
        });
    }

    private void getFavoriteResponse() {
        viewModel.getSetFavorite().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
            }
        });
    }


    private void setProductFavorite() {
        favoriteRequest.setProduct_id(model.getId());
        helper.showLoading(requireActivity());
        if (model.getFav()) {
            binding.favIcon.setImageResource(R.drawable.favorite_item);
            model.setFav(false);
        } else {
            binding.favIcon.setImageResource(R.drawable.ic_favorite_active);
            model.setFav(true);
        }
        viewModel.setFavorite(favoriteRequest);
    }

    private void productQty() {
        binding.addItem.setOnClickListener(view -> binding.productCount.setText(String.valueOf(viewModel.add(Integer.parseInt(binding.productCount.getText().toString())))));
        binding.subtractItem.setOnClickListener(view -> binding.productCount.setText(String.valueOf(viewModel.sub(Integer.parseInt(binding.productCount.getText().toString())))));
    }

    private void navigateToAllReviews() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        Navigation.findNavController(requireView()).navigate(R.id.action_product_details_to_allReviewsFragment, bundle);
    }

    private void showSizeChartDialog() {
        SizeChartDialog sizeChartDialog = new SizeChartDialog();
        FragmentActivity activity = (FragmentActivity) (getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("image", model.getSizeChart());
        FragmentManager fm = activity.getSupportFragmentManager();
        sizeChartDialog.setArguments(bundle);
        sizeChartDialog.show(fm, "fragment_alert");
    }

    private void addToCart(){
        binding.addToCart.setOnClickListener(view -> {
            helper.showLoading(requireActivity());
            AddToCartRequest request = new AddToCartRequest();
            request.setProduct_id(model.getId());
            request.setSize_id(size_id);
            request.setColor_id(color_id);
            request.setQty(Integer.parseInt(binding.productCount.getText().toString()));
            viewModel.addToCart(request);
            addToCartResponse();
        });
    }

    private void addToCartResponse(){
        viewModel.getAddToCart().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartProductsModel>>() {
            @Override
            public void onChanged(BaseModel<CartProductsModel> model) {
                helper.dismissLoading();
                Navigation.findNavController(requireView()).navigate(R.id.action_product_details_to_cartFragment);
            }
        });
    }

}