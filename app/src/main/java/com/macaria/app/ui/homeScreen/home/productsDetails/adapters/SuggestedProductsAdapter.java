package com.macaria.app.ui.homeScreen.home.productsDetails.adapters;

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
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.ProductModel;
import com.macaria.app.ui.homeScreen.home.products.models.SuggestedProducts;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SuggestedProductsListener;

import java.util.ArrayList;
import java.util.List;


public class SuggestedProductsAdapter extends RecyclerView.Adapter<SuggestedProductsAdapter.StoryViewHolder> {
    private List<SuggestedProducts> data = new ArrayList<>();
    private Context context;
    private SuggestedProductsListener listener;
    private boolean isFinishedLoading;

    public SuggestedProductsAdapter(Context context, SuggestedProductsListener listener) {
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
            holder.itemView.setOnClickListener(view -> listener.onSuggestedProductsClicked(data.get(position).getId()));
            if (data.get(position).getIsFav())holder.binding.favorite.setImageResource(R.drawable.ic_products_fav_fill);

            holder.binding.favorite.setOnClickListener(view -> {
                listener.onFavoriteClick(data.get(position).getId());
                if (data.get(position).getIsFav()){
                    holder.binding.favorite.setImageResource(R.drawable.ic_products_fav_strok);
                    data.get(position).setIsFav(false);
                }
                else {
                    holder.binding.favorite.setImageResource(R.drawable.ic_products_fav_fill);
                    data.get(position).setIsFav(true);
                }
            });

        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<SuggestedProducts> data) {
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
