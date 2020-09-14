package com.wipro.musicworld.services;

import com.wipro.musicworld.entities.DataEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArtistApi {
    @GET("/2.0")
    Call<DataEntity> searchArtist(@Query("method") String methodName, @Query("artist") String artist,
                                   @Query("api_key") String api_key, @Query("format") String format);
}
