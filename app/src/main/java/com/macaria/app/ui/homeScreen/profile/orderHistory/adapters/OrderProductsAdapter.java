package com.macaria.app.ui.homeScreen.profile.orderHistory.adapters;

import static com.macaria.app.data.Constants.DELIVERY;
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
import com.macaria.app.databinding.ProductsItemBinding;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderDetailsModel;

import java.util.ArrayList;
import java.util.List;


public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.StoryViewHolder> {
    private List<OrderDetailsModel> data = new ArrayList<>();
    private Context context;
    private ProductsListener listener;
    private boolean isFinishedLoading;
    private String status ;

    public OrderProductsAdapter(Context context, ProductsListener listener, String status) {
        this.context = context;
        this.listener = listener;
        this.status = status;

    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(ProductsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.quantity.setText(context.getString(R.string.qty).concat(": ").concat(data.get(position).getQty()));
            holder.binding.color.setText(context.getString(R.string.color).concat(": "));
            holder.binding.size.setText(context.getString(R.string.size).concat(": ").concat(data.get(position).getSize()));
            holder.binding.price.setText(data.get(position).getPrice().concat(" ").concat(context.getString(R.string.egp)));
            holder.binding.image.setClipToOutline(true);
            holder.binding.title.setText(data.get(position).getName());
            loadImage(context, data.get(position).getImage(), R.drawable.profile_holder, holder.binding.image);
            holder.binding.colorView.setCardBackgroundColor(Color.parseColor(data.get(position).getColor()));
            if (status.equals(DELIVERY))holder.binding.reviewItem.setVisibility(View.VISIBLE);
            holder.binding.reviewItem.setOnClickListener(view -> listener.onReviewItemClicked());
            holder.binding.reviewItem.setVisibility(View.VISIBLE);
        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<OrderDetailsModel> data) {
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

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        ProductsItemBinding binding;

        public StoryViewHolder(@NonNull ProductsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
