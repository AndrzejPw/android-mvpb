package com.codeprogression.mvpb.core;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

import com.codeprogression.mvpb.model.IMDBService;

/**
 * Created by andrzej.biernacki on 2016-04-15.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    IMDBService provideImdbService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IMDBService.class);
    }
}
