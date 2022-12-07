package com.macaria.app.di;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.macaria.app.ui.authorization.AuthData;
import com.macaria.app.ui.homeScreen.favorite.models.SetFavoriteRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.utilities.Loading;
import com.macaria.app.utilities.MyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

    @Provides
    public Handler providerHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    public MyHelper provideHelper(){return new MyHelper();}

    @Provides
    public Loading provideLoading(){return new Loading();}

    @Provides
    public SetFavoriteRequest provideFavoriteRequest(){return new SetFavoriteRequest();}

    @Provides
    public AddAddressRequest provideAddressRequest(){return new AddAddressRequest();}

}
