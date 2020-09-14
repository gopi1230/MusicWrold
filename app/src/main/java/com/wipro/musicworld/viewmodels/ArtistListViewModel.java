package com.wipro.musicworld.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.wipro.musicworld.entities.DataEntity;
import com.wipro.musicworld.repositories.MusicRepository;
import javax.inject.Inject;
public class ArtistListViewModel extends ViewModel {

    private MutableLiveData<DataEntity> mutableLiveData;
    @Inject
    MusicRepository musicRepository;

    @ViewModelInject
   public   ArtistListViewModel(MusicRepository musicRepository){
        this.musicRepository=musicRepository;
    }

    public void getSearchArtistResults(String methodName,String artist,String apikey,String format){
            mutableLiveData=musicRepository.getSearchArtistResults(methodName,artist,apikey,format);
    }

    public LiveData<DataEntity> getMutableLiveData(){
        return mutableLiveData;
    }


}