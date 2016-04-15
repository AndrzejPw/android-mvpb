package com.codeprogression.mvpb.core;

import timber.log.Timber;

import android.app.Application;
import android.content.Context;
import com.codeprogression.mvpb.BuildConfig;

/**
 * Created by andrzej.biernacki on 2016-04-15.
 */
public class MyApplication extends Application {

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .serviceModule(new ServiceModule())
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
