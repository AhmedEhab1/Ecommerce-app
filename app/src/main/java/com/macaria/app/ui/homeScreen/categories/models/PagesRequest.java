package com.macaria.app.ui.homeScreen.categories.models;

public class PagesRequest {
    int category_id , size_id , color_id, sort_by, price_from , price_to ;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public int getSort_by() {
        return sort_by;
    }

    public void setSort_by(int sort_by) {
        this.sort_by = sort_by;
    }

    public int getPrice_from() {
        return price_from;
    }

    public void setPrice_from(int price_from) {
        this.price_from = price_from;
    }

    public int getPrice_to() {
        return price_to;
    }

    public void setPrice_to(int price_to) {
        this.price_to = price_to;
    }
}
