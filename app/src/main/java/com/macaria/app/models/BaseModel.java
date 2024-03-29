package com.macaria.app.models;

import androidx.room.TypeConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseModel<T> {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("item")
    @Expose
    private Item<T> item;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Item<T> getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Item<T> implements Serializable {
        @SerializedName("data")
        @Expose
        private T data;
        @SerializedName("meta")
        @Expose
        private MetaModel meta;

        public MetaModel getMeta() {
            return meta;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

    }
}
