package com.macaria.app.ui.homeScreen.home.products.adapter;

import static com.macaria.app.utilities.ImageHelper.loadImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.R;
import com.macaria.app.databinding.FavoriteItemBinding;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrdersProductsListener;

import java.util.ArrayList;
import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.StoryViewHolder> {
    private List<ProductModel> data = new ArrayList<>();
    private Context context;
    private ProductsListener listener;
    private boolean isFinishedLoading;

    public ProductsAdapter(Context context, ProductsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.price.setText(data.get(position).getPrice().concat(" ").concat(context.getString(R.string.egp)));
            holder.binding.image.setClipToOutline(true);
            holder.binding.title.setText(data.get(position).getName());
            loadImage(context, data.get(position).getImage(), R.drawable.profile_holder, holder.binding.image);
        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<ProductModel> data) {
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
        FavoriteItemBinding binding;

        public StoryViewHolder(@NonNull FavoriteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
