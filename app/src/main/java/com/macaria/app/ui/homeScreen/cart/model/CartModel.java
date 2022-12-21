package com.macaria.app.ui.homeScreen.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

import java.io.Serializable;
import java.util.List;

public class CartModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("item_count")
    @Expose
    private Integer itemCount;
    @SerializedName("item_pirce")
    @Expose
    private String itemPrice;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("delivery_fee")
    @Expose
    private String deliveryFee;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("address")
    @Expose
    private AddressModel address;
    @SerializedName("orderDetails")
    @Expose
    private BaseModel.Item<List<CartProductsModel>>  cartProductsModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public BaseModel.Item<List<CartProductsModel>> getCartProductsModel() {
        return cartProductsModel;
    }

    public void setCartProductsModel(BaseModel.Item<List<CartProductsModel>> cartProductsModel) {
        this.cartProductsModel = cartProductsModel;
    }
}
