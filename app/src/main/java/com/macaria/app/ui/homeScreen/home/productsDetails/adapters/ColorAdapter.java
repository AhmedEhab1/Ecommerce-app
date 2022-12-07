package com.macaria.app.ui.homeScreen.home.productsDetails.adapters;

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
import com.macaria.app.databinding.ColorItemBinding;
import com.macaria.app.databinding.SizeItemBinding;
import com.macaria.app.ui.homeScreen.home.products.models.ColorModel;
import com.macaria.app.ui.homeScreen.home.products.models.SizeModel;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.ColorListener;
import com.macaria.app.ui.homeScreen.home.productsDetails.listeners.SizeListener;

import java.util.ArrayList;
import java.util.List;


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.StoryViewHolder> {
    private List<ColorModel> data = new ArrayList<>();
    private Context context;
    private ColorListener listener;
    private boolean isFinishedLoading;
    private int selectedItem = -1 ;

    public ColorAdapter(Context context, ColorListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(ColorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.binding.color.setCardBackgroundColor(Color.parseColor(data.get(position).getColorHex()));

            if (selectedItem == position){
                holder.binding.frame.setBackgroundResource(R.drawable.color_item_bg);
            }else {
                holder.binding.frame.setBackgroundResource(0);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = position ;
                    listener.onColorSelected(data.get(position).getId());
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
        ColorItemBinding binding;

        public StoryViewHolder(@NonNull ColorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
