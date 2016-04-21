package com.codeprogression.mvpb.core;

import timber.log.Timber;

import android.app.Application;
import android.content.Context;
import android.media.Image;
import com.codeprogression.mvpb.BuildConfig;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
        initImageLoader();
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions);
        if (BuildConfig.DEBUG) {
            config.writeDebugLogs();
        }
        ImageLoader.getInstance().init(config.build());
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
