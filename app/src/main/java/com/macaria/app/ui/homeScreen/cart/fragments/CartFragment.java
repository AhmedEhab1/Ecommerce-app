package com.macaria.app.ui.homeScreen.cart.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;

import com.macaria.app.databinding.CartFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.general.backFragments.BackFragment;
import com.macaria.app.ui.homeScreen.cart.adapter.CartProductListener;
import com.macaria.app.ui.homeScreen.cart.adapter.CartProductsAdapter;
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartModel;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.cart.vm.CartViewModel;
import com.macaria.app.ui.homeScreen.favorite.vm.FavoriteViewModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsAdapter;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.utilities.MyHelper;
import com.macaria.app.utilities.RecyclerViewSwipeHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment implements CartProductListener {
    private CartFragmentBinding binding;
    private CartViewModel viewModel;
    private CartModel model;

    public CartFragment() {
        // Required empty public constructor
    }

    @Inject
    MyHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        helper.showLoading(requireActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        binding.checkOut.setOnClickListener(view -> proceedCheckOut());
        binding.keepShopping.setOnClickListener(view -> Navigation.findNavController(requireView()).popBackStack());
        getData();
        onItemSwiped();
        getCartModel();
        addOrSubItemResponse();
        deleteItemResponse();
        addPromoCode();
        errorMessage();
    }

    private void getData() {
        viewModel.getCart();
    }

    private void onItemSwiped() {
        RecyclerViewSwipeHelper swipeHelper = new RecyclerViewSwipeHelper(requireActivity(), binding.recycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        "",
                        ResourcesCompat.getDrawable(requireActivity().getResources(),
                                R.drawable.ic_delete_cart_item, requireActivity().getTheme()),
                        Color.parseColor("#000000"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                helper.showLoading(requireActivity());
                                viewModel.deleteItem(model.getCartProductsModel().getData().get(pos).getId());
                            }
                        }
                ));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(binding.recycler);
    }

    private void errorMessage() {
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity(), null, s);
            }
        });
    }

    private void getCartModel() {
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> cartModelBaseModel) {
                cartsResponse(cartModelBaseModel);
            }
        });
    }

    private void cartsResponse(BaseModel<CartModel> cartModelBaseModel) {
        helper.dismissLoading();
        model = cartModelBaseModel.getItem().getData();
        if (model != null) {
            binding.totalPrice.setText(model.getTotalPrice().concat(" ").concat(getString(R.string.egp)));
            initRec(model.getCartProductsModel());
        }
        cartDataVisible();
    }

    private void cartDataVisible() {
        if (model != null) {
            if (model.getCartProductsModel().getData().size() == 0) {
                binding.emptyCart.setVisibility(View.VISIBLE);
                binding.cartData.setVisibility(View.GONE);
            }    else {
                binding.emptyCart.setVisibility(View.GONE);
                binding.cartData.setVisibility(View.VISIBLE);
            }
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.cartData.setVisibility(View.GONE);
        }
    }

    private void initRec(BaseModel.Item<List<CartProductsModel>> listBaseModel) {
        CartProductsAdapter adapter = new CartProductsAdapter(getActivity(), this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.recycler.setNestedScrollingEnabled(true);
        adapter.addData(listBaseModel.getData());
    }

    private void addOrSubItemResponse() {
        viewModel.getAddOrSubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> cartModelBaseModel) {
                cartsResponse(cartModelBaseModel);
            }
        });
    }

    @Override
    public void onAddItemClicked(AddToCartRequest request) {
        helper.showLoading(requireActivity());
        viewModel.addItem(request);
    }

    @Override
    public void onSubItemClicked(AddToCartRequest request) {
        helper.showLoading(requireActivity());
        viewModel.subItem(request);
    }

    private void deleteItemResponse() {
        viewModel.getDeleteItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> cartModelBaseModel) {
                cartsResponse(cartModelBaseModel);
            }
        });
    }

    private void addPromoCode() {
        binding.applyPromoCode.setOnClickListener(view -> {
            helper.showLoading(requireActivity());
            viewModel.promoCode(binding.promoCodeEditText.getText().toString());
            promoCodeResponse();
        });
    }

    private void promoCodeResponse() {
        viewModel.getPromoCodeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<CartModel>>() {
            @Override
            public void onChanged(BaseModel<CartModel> baseModel) {
                helper.dismissLoading();
                if (baseModel.getSuccess()) {
                    binding.addPromoCode.setVisibility(View.GONE);
                    binding.promoCodeSuccess.setVisibility(View.VISIBLE);
                    binding.promoCodeText.setText(model.getCartProductsModel().getData().get(0).getPercentage());
                } else {
                    helper.showToast(getActivity(), baseModel.getMessage());
                }
            }
        });
    }

    private void proceedCheckOut() {
        Bundle args = new Bundle();
        args.putString("key", "cart");
        args.putSerializable("cartModel", model);
        Navigation.findNavController(requireView()).navigate(R.id.action_cartFragment_to_savedAddressesFragment, args);
    }
}