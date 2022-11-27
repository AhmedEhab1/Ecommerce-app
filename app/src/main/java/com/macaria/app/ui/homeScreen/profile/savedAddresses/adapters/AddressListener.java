package com.macaria.app.ui.homeScreen.profile.savedAddresses.adapters;

import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;

public interface AddressListener {
    void onDeleteAddress(int id);
    void onEditAddress(AddressModel model);
}
