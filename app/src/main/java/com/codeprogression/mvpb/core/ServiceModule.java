package com.codeprogression.mvpb.core;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

import com.codeprogression.mvpb.BuildConfig;
import com.codeprogression.mvpb.model.IMDBService;

/**
 * Created by andrzej.biernacki on 2016-04-15.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    IMDBService provideImdbService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        if (BuildConfig.DEBUG) {
            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(IMDBService.class);
    }
}
