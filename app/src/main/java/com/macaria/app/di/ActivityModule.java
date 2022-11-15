package com.macaria.app.di;


import android.os.Handler;
import android.os.Looper;

import com.macaria.app.utilities.Loading;
import com.macaria.app.utilities.MyHelper;

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
    public Loading loading(){return new Loading();}

}
