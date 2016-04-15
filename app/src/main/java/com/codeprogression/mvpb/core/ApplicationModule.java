package com.codeprogression.mvpb.core;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import android.content.Context;

@Module
public class ApplicationModule {
    private MyApplication myApplication;

    public ApplicationModule(final MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Singleton
    @Provides
    public MyApplication providesMyApplication(){
        return myApplication;
    }

    @Singleton
    @Provides
    public Context providesContext(){
        return myApplication;
    }
}
