package com.macaria.app.ui.homeScreen.home.products.adapter;

import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

public interface ProductsListener {
    void onProductClick(ProductModel id);
    void onFavoriteClick(int id);
}
