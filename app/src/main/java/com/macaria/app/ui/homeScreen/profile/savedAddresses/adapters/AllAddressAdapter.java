package com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.databinding.AddressItemBinding;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

import java.util.ArrayList;
import java.util.List;


public class AllAddressAdapter extends RecyclerView.Adapter<AllAddressAdapter.StoryViewHolder> {
    private List<AddressModel> data = new ArrayList<>();
    private Context context;
    private AddressListener listener ;
    private boolean isFinishedLoading;

    public AllAddressAdapter(Context context, AddressListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(AddressItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.userName.setText(data.get(position).getFirstName().concat(" ").concat(data.get(position).getLastName()));
        holder.binding.address.setText(data.get(position).getAddress());
        holder.binding.city.setText(data.get(position).getCity());
        holder.binding.country.setText(data.get(position).getCountry());
        holder.binding.phone.setText(data.get(position).getPhone());
        holder.binding.deleteAddress.setOnClickListener(view -> {
            listener.onDeleteAddress(data.get(position).getId());
            data.remove(position);
            notifyDataSetChanged();
        });
        holder.binding.editAddress.setOnClickListener(view -> listener.onEditAddress(data.get(position)));
        holder.itemView.setOnClickListener(view -> listener.onAddressClicked(data.get(position).getId(), data.get(position)));
    }

    public void addData(List<AddressModel> data) {
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
        AddressItemBinding binding;

        public StoryViewHolder(@NonNull AddressItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
