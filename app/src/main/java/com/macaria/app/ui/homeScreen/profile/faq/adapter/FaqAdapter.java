package com.macaria.app.ui.homeScreen.profile.faq.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.macaria.app.databinding.FaqItemBinding;
import com.macaria.app.ui.homeScreen.profile.faq.models.FaqModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryListener;

import java.util.ArrayList;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.StoryViewHolder> {
    private List<FaqModel> data = new ArrayList<>();
    private Context context;
    private boolean isFinishedLoading;
    private boolean show = true;

    public FaqAdapter(Context context ) {
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(FaqItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.description.setText(data.get(position).getDescription());
        holder.binding.name.setText(data.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Transition transition = new Fade();
            transition.setDuration(400);
            transition.addTarget(holder.binding.description);
            TransitionManager.beginDelayedTransition(holder.binding.view, transition);
            holder.binding.description.setVisibility(show ? View.VISIBLE : View.GONE);
            holder.binding.arrow.setRotation(show ? 180 : 360);
            show = !show;
        });
    }

    public void addData(List<FaqModel> data) {
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
        FaqItemBinding binding;

        public StoryViewHolder(@NonNull FaqItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}