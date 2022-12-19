package com.macaria.app.ui.homeScreen.cart.adapter;

import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;

public interface CartProductListener {
    void onAddItemClicked(AddToCartRequest request);
    void onSubItemClicked(AddToCartRequest request);
}
