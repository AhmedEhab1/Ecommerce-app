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
import com.macaria.app.databinding.SizeItemBinding;
import com.macaria.app.ui.homeScreen.home.products.adapter.ProductsListener;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.products.models.SuggestedProducts;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;

import java.util.ArrayList;
import java.util.List;


public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.StoryViewHolder> {
    private List<SizeModel> data = new ArrayList<>();
    private Context context;
    private SizeListener listener;
    private boolean isFinishedLoading;

    public SizeAdapter(Context context, SizeListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(SizeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.name.setText(data.get(position).getName());
        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<SizeModel> data) {
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
        SizeItemBinding binding;

        public StoryViewHolder(@NonNull SizeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
