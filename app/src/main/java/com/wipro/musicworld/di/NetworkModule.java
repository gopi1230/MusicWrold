package com.wipro.musicworld.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wipro.musicworld.repositories.MusicRepository;
import com.wipro.musicworld.services.ArtistApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {
    @Singleton
    @Provides
    public static ArtistApi provideArtistApi( ) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl("https://ws.audioscrobbler.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ArtistApi.class);
    }
}
