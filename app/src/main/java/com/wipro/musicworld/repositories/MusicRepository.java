package com.wipro.musicworld.repositories;


import androidx.lifecycle.MutableLiveData;
import com.wipro.musicworld.entities.DataEntity;
import com.wipro.musicworld.services.ArtistApi;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRepository {

    private ArtistApi artistApi;
    public MutableLiveData<DataEntity> resultsMutableLiveData= new MutableLiveData<>();



    @Inject
    public MusicRepository(ArtistApi artistApi){
        this.artistApi=artistApi;
    }

    public MutableLiveData<DataEntity> getSearchArtistResults(String methodName, String artist,  String apikey, String format){


       Call<DataEntity> entityCall = artistApi.searchArtist(methodName,artist,apikey,format);

      entityCall.enqueue(new Callback<DataEntity>() {
           @Override
           public void onResponse(Call<DataEntity> call, Response<DataEntity> response) {
               if(response.isSuccessful()){
                   resultsMutableLiveData.setValue(response.body());
               }else {
                   resultsMutableLiveData.setValue(null);
               }
           }
           @Override
           public void onFailure(Call<DataEntity> call, Throwable t) {
               resultsMutableLiveData.setValue(null);
           }
       });

        return resultsMutableLiveData;
    }
}
