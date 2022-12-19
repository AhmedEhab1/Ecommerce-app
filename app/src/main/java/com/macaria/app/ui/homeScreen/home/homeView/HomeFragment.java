package com.macaria.app.ui.homeScreen.home.homeView;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.HomeFragmentBinding;
import com.macaria.app.databinding.ProductDetailsFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.home.homeView.adapters.HomeCategoryAdapter;
import com.macaria.app.ui.homeScreen.home.homeView.adapters.HomeSliderAdapter;
import com.macaria.app.ui.homeScreen.home.homeView.listeners.CategoriesListener;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.home.homeView.models.HomeModel;
import com.macaria.app.ui.homeScreen.home.homeView.vm.HomeViewModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.SizeAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.adapters.SliderAdapter;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;
import com.macaria.app.utilities.MyHelper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements CategoriesListener, ProductsListener {
    private HomeFragmentBinding binding;
    private HomeModel model;
    private HomeViewModel viewModel;

    @Inject
    MyHelper helper;

    @Inject
    SetFavoriteRequest favoriteRequest;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding == null) {
            binding = HomeFragmentBinding.inflate(inflater, container, false);
            init();
        }
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        errorMessage();
        homeResponse();
        setHomeData();
        initCategoryRec();
        viewCart();
    }

    private void setHomeData() {
        model = viewModel.getHomeData();
        bannersBg();
        initNewArrivalRec();
        initBestSellerRec();
    }

    private void initCategoryRec() {
        HomeCategoryAdapter HomeCategoryAdapter = new HomeCategoryAdapter(getActivity(), this);
        binding.categoryRec.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        binding.categoryRec.setAdapter(HomeCategoryAdapter);
        HomeCategoryAdapter.addData(viewModel.getCategoriesList());
        initSliderRec(viewModel.getCategoriesList());
    }

    @Override
    public void onCategorySelected(int id) {
        helper.showLoading(requireActivity());
        viewModel.getHome(id);
    }

    private void bannersBg() {
        binding.firstAds.setClipToOutline(true);
        binding.secondAds.setClipToOutline(true);
        binding.thirdAds.setClipToOutline(true);
        binding.footerAds1.setClipToOutline(true);
        binding.footerAds2.setClipToOutline(true);
        setBannersData();
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void homeResponse() {
        viewModel.getHomeModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<HomeModel>>() {
            @Override
            public void onChanged(BaseModel<HomeModel> homeModelBaseModel) {
                helper.dismissLoading();
                setHomeData();
            }
        });
    }

    private void setBannersData() {
        loadImage(getActivity(), model.getFirstAds().getData().getImage(), R.drawable.home_logo, binding.firstAds);
        loadImage(getActivity(), model.getSecondAds().getData().getImage(), R.drawable.home_logo, binding.secondAds);
        loadImage(getActivity(), model.getThirdAds().getData().getImage(), R.drawable.home_logo, binding.thirdAds);
        loadImage(getActivity(), model.getFooterAds().getData().get(0).getImage(), R.drawable.home_logo, binding.footerAds1);
        loadImage(getActivity(), model.getFooterAds().getData().get(1).getImage(), R.drawable.home_logo, binding.footerAds2);
    }

    private void initNewArrivalRec() {
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), this);
        binding.newArrivalsRec.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.newArrivalsRec.setAdapter(productsAdapter);
        productsAdapter.addData(model.getNewArrivals().getData());
    }

    private void initBestSellerRec() {
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(), this);
        binding.besSellerRec.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.besSellerRec.setAdapter(productsAdapter);
        productsAdapter.addData(model.getBestSellers().getData());
    }

    private void initSliderRec(List<CategoriesModel> model) {
        SliderView sliderView = binding.imageSlider;
        HomeSliderAdapter adapter = new HomeSliderAdapter(requireActivity());
        sliderView.setSliderAdapter(adapter);
        adapter.renewItems(model);
       // sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    @Override
    public void onProductClick(ProductModel id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProductModel", id);
        bundle.putString("type", "model");
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_product_details, bundle);
    }

    private void viewCart(){
        binding.cart.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_cartFragment);
        });
    }

    @Override
    public void onFavoriteClick(int id) {
        helper.showLoading(requireActivity());
        favoriteRequest.setProduct_id(id);
        viewModel.setFavorite(favoriteRequest);
        getSetFavoriteResponse();
    }

    private void getSetFavoriteResponse() {
        viewModel.getSetFavorite().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
            }
        });
    }
}