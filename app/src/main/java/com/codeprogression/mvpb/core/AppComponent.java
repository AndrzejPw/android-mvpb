package com.codeprogression.mvpb.core;

import dagger.Component;

import javax.inject.Singleton;

import com.codeprogression.mvpb.view.MainActivity;
import com.codeprogression.mvpb.view.MainView;

@Singleton
@Component(modules = {ServiceModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(MainView mainView);
}
