package com.macaria.app.ui.homeScreen.cart.adapter;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.R;
import com.macaria.app.databinding.CartItemBinding;
import com.macaria.app.databinding.FavoriteItemBinding;
import com.macaria.app.ui.homeScreen.cart.model.AddToCartRequest;
import com.macaria.app.ui.homeScreen.cart.model.CartProductsModel;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.StoryViewHolder> {
    private List<CartProductsModel> data = new ArrayList<>();
    private Context context;
    private CartProductListener listener;
    private boolean isFinishedLoading;

    public CartProductsAdapter(Context context, CartProductListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public CartProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            CartProductsModel model = data.get(position);
            holder.binding.price.setText(model.getPrice().concat(" ").concat(context.getString(R.string.egp)));
            holder.binding.image.setClipToOutline(true);
            holder.binding.title.setText(model.getName());
            holder.binding.productNumber.setText(model.getQty());
            loadImage(context, model.getImage(), R.drawable.ic_placeholder, holder.binding.image);
            holder.binding.size.setText(context.getString(R.string.size).concat(" : ").concat(model.getSize()));
            holder.binding.color.setCardBackgroundColor(Color.parseColor(model.getColor()));
            AddToCartRequest request = new AddToCartRequest();
            request.setProduct_id(model.getItemId());
            request.setSize_id(model.getSizeId());
            holder.binding.add.setOnClickListener(view -> listener.onAddItemClicked(request));
            holder.binding.sub.setOnClickListener(view -> listener.onSubItemClicked(request));

            if (listener == null){
                holder.binding.addSubCartBg.setVisibility(View.GONE);
            }else holder.binding.addSubCartBg.setVisibility(View.VISIBLE);
        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<CartProductsModel> data) {
        int lastIndex = this.data.isEmpty() ? 0 : this.data.size() - 1;
        this.data.addAll(data);
        int newLastIndex = this.data.size();
        notifyItemRangeChanged(lastIndex, newLastIndex);
    }

    public void setFinishedLoading(boolean finishedLoading) {
        isFinishedLoading = finishedLoading;
        if (!data.isEmpty())
            notifyItemChanged(data.size() - 1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        int count = this.data.size();
        data.clear();
        notifyItemRangeRemoved(0, count);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        CartItemBinding binding;

        public StoryViewHolder(@NonNull CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
