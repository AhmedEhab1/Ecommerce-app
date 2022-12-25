package com.macaria.app.ui.homeScreen.home.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.databinding.AddressItemBinding;
import com.macaria.app.databinding.TrendItemBinding;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters.AddressListener;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

import java.util.ArrayList;
import java.util.List;


public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.StoryViewHolder> {
    private List<CategoriesModel> data = new ArrayList<>();
    private Context context;
    private SearchListener listener ;
    private boolean isFinishedLoading;

    public TrendAdapter(Context context, SearchListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(TrendItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.title.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(view -> listener.onCategoryTrendClicked(data.get(position).getId()));
    }

    public void addData(List<CategoriesModel> data) {
        int lastIndex =this.data.isEmpty()?0: this.data.size()-1;
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
        TrendItemBinding binding;

        public StoryViewHolder(@NonNull TrendItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
