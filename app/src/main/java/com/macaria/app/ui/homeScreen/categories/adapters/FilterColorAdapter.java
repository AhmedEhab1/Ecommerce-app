package com.macaria.app.ui.homeScreen.categories.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.R;
import com.macaria.app.databinding.FilterColorItemBinding;
import com.macaria.app.ui.homeScreen.categories.listeners.FilterDialogListener;
import com.macaria.app.ui.homeScreen.home.products.models.ColorModel;

import java.util.ArrayList;
import java.util.List;


public class FilterColorAdapter extends RecyclerView.Adapter<FilterColorAdapter.StoryViewHolder> {
    private List<ColorModel> data = new ArrayList<>();
    private Context context;
    private FilterDialogListener listener;
    private boolean isFinishedLoading;
    private int selectedItem = 0 ;

    public FilterColorAdapter(Context context, FilterDialogListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(FilterColorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.color.setCardBackgroundColor(Color.parseColor(data.get(position).getColorHex()));
            holder.binding.title.setText(data.get(position).getName());
            if (selectedItem == position){
                holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.black));
            }else {
                holder.binding.title.setTextColor(ContextCompat.getColor(context, R.color.textColorGray));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = position ;
                    listener.onColorClicked(data.get(position).getId());
                    notifyDataSetChanged();
                }
            });

        }catch (Exception e){
            Log.e("crash", "onBindViewHolder: ",e );
        }
    }

    public void addData(List<ColorModel> data) {
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
        FilterColorItemBinding binding;

        public StoryViewHolder(@NonNull FilterColorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
