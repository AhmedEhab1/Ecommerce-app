package com.macaria.app.ui.homeScreen.profile.orderHistory.adapters;

import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;

public interface OrderHistoryListener {
    void onDetailsClicked(OrderHistoryModel model);
    void onReOrderClicked(OrderHistoryModel model);
}
