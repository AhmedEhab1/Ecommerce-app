package com.macaria.app.ui.homeScreen.profile.orderHistory.adapters;

import static com.macaria.app.data.Constants.CANCEL;
import static com.macaria.app.data.Constants.DELIVERY;
import static com.macaria.app.data.Constants.PENDING;
import static com.macaria.app.data.Constants.PREPARING;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.macaria.app.R;
import com.macaria.app.databinding.AddressItemBinding;
import com.macaria.app.databinding.OrderHistoryItemBinding;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters.AddressListener;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

import java.util.ArrayList;
import java.util.List;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.StoryViewHolder> {
    private List<OrderHistoryModel> data = new ArrayList<>();
    private Context context;
    private OrderHistoryListener listener;
    private boolean isFinishedLoading;

    public OrderHistoryAdapter(Context context, OrderHistoryListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(OrderHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.orderReference.setText("#".concat(data.get(position).getOrderNumber()));
        holder.binding.placedOn.setText(data.get(position).getDate());
        holder.binding.totalPrice.setText(data.get(position).getTotalPrice().concat(" ").concat(context.getString(R.string.egp)));
        holder.binding.payment.setText(data.get(position).getPaymentType());
        String status = data.get(position).getStatus();
        holder.binding.status.setText(status);

//        holder.binding.deleteAddress.setOnClickListener(view -> {
//            listener.onDeleteAddress(data.get(position).getId());
//            data.remove(position);
//            notifyDataSetChanged();
//        });

        if (status.equals(PENDING)) {
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.lightGreyColor));
        } else if (status.equals(PREPARING)) {
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.orangeColor));
        } else if (status.equals(DELIVERY)) {
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.greenColor));
        } else if (status.equals(CANCEL)) {
            holder.binding.status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }


    public void addData(List<OrderHistoryModel> data) {
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
        OrderHistoryItemBinding binding;

        public StoryViewHolder(@NonNull OrderHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
