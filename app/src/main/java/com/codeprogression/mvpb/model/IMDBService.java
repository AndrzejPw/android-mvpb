package com.codeprogression.mvpb.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by andrzej.biernacki on 2016-04-14.
 */
public interface IMDBService {

    @GET("/")
    Call<IMDBResponse> searchIMDB(@Query("s") String searchText);
}


