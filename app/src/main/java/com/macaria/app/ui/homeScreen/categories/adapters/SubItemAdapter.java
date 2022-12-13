package com.macaria.app.ui.homeScreen.categories.adapters;

import static com.macaria.app.utilities.ImageHelper.loadImage;

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
import com.macaria.app.databinding.SubCategoryItemBinding;
import com.macaria.app.databinding.SubItemBinding;
import com.macaria.app.ui.homeScreen.home.homeView.models.CategoriesModel;

import java.util.ArrayList;
import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.StoryViewHolder> {
    private List<CategoriesModel> data = new ArrayList<>();
    private Context context;
    private SubItemsListener listener;
    private boolean isFinishedLoading;

    public SubItemAdapter(Context context, SubItemsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(SubItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.image.setClipToOutline(true);
            holder.binding.title.setText(data.get(position).getName());
            loadImage(context, data.get(position).getImage(), R.drawable.ic_placeholder, holder.binding.image);
            holder.itemView.setOnClickListener(view -> listener.onSubItemClicked(data.get(position).getId()));
        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<CategoriesModel> data) {
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
        SubItemBinding binding;

        public StoryViewHolder(@NonNull SubItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
