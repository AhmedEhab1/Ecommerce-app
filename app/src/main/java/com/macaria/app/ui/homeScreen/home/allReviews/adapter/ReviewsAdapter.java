package com.macaria.app.ui.homeScreen.home.allReviews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.R;
import com.macaria.app.databinding.AllReviewsItemBinding;
import com.macaria.app.databinding.SizeItemBinding;
import com.macaria.app.ui.homeScreen.home.products.models.ReviewModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;

import java.util.ArrayList;
import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.StoryViewHolder> {
    private List<ReviewModel> data = new ArrayList<>();
    private Context context;
    private boolean isFinishedLoading;

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(AllReviewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.title.setText(data.get(position).getTitle());
            holder.binding.description.setText(data.get(position).getReview());
            holder.binding.date.setText(data.get(position).getCreatedAt());
            holder.binding.byUser.setText(context.getString(R.string.by).concat(data.get(position).getCreatedAt()));
            holder.binding.rate.setRating(data.get(position).getRate());
        } catch (Exception e) {
            Log.e("crash", "onBindViewHolder: ", e);
        }
    }

    public void addData(List<ReviewModel> data) {
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
        AllReviewsItemBinding binding;

        public StoryViewHolder(@NonNull AllReviewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
