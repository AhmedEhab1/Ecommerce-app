package com.macaria.app.ui.homeScreen.profile.orderHistory.models;

public class AddReviewRequest {
    String review , title , rate ,product_id ;

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public String getTitle() {
        return title;
    }

    public String getRate() {
        return rate;
    }

    public String getProduct_id() {
        return product_id;
    }
}
