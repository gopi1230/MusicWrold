package com.wipro.musicworld;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.wipro.musicworld.entities.DataEntity;
import com.wipro.musicworld.repositories.MusicRepository;
import com.wipro.musicworld.viewmodels.ArtistListViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class ArtistListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MusicRepository musicRepository;

    private ArtistListViewModel viewModel;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        viewModel=new ArtistListViewModel(musicRepository);
    }

   @Test
   public void searchArtistDataWithInvalidMethodNameTest(){
       String method="artist.seearch";
       String artist="cher";
       String apikey="cbaebad167e697788489d2b99992da60";
       String format="json";

       Mockito.when(musicRepository.getSearchArtistResults(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
              ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
               .thenReturn(mockErrorResponse());
       viewModel.getSearchArtistResults(method,artist,apikey,format);

       Assert.assertNotNull(viewModel.getMutableLiveData());
       Assert.assertNull(viewModel.getMutableLiveData().getValue());
   }

    @Test
    public void searchArtistEmptyDataTest(){
        String method="artist.seearch";
        String artist="";
        String apikey="cbaebad167e697788489d2b99992da60";
        String format="json";

        Mockito.when(musicRepository.getSearchArtistResults(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(mockEmptyResponse());
        viewModel.getSearchArtistResults(method,artist,apikey,format);

        Assert.assertNotNull(viewModel.getMutableLiveData());
        Assert.assertNull(viewModel.getMutableLiveData().getValue().getResults());
    }
    @Test
    public void searchArtistDataWithValidDetailsTest(){
          String method="artist.search";
          String artist="cher";
          String apikey="cbaebad167e697788489d2b99992da60";
          String format="json";

        Mockito.when(musicRepository.getSearchArtistResults(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
               ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(mockResponse());
          viewModel.getSearchArtistResults(method,artist,apikey,format);

        Assert.assertNotNull(viewModel.getMutableLiveData());
        Assert.assertEquals(1,viewModel.getMutableLiveData().getValue().getResults().getArtistmatches().getArtist().size());

    }

    public  MutableLiveData<DataEntity> mockErrorResponse(){
        MutableLiveData<DataEntity> data = new MutableLiveData<>();
        data.setValue(null);
        return data;
    }
    public  MutableLiveData<DataEntity> mockEmptyResponse(){
        DataEntity entity= new DataEntity();
        MutableLiveData<DataEntity> data = new MutableLiveData<>();
        data.setValue(entity);
        return data;
    }
    public  MutableLiveData<DataEntity> mockResponse(){
        String res="{\"results\":{\"opensearch:Query\":{\"#text\":\"\",\"role\":\"request\",\"searchTerms\":\"oliver\",\"startPage\":\"2\"},\"opensearch:totalResults\":\"57198\",\"opensearch:startIndex\":\"30\",\"opensearch:itemsPerPage\":\"30\",\"artistmatches\":{\"artist\":[{\"name\":\"Oliver $ & Jimi Jules\",\"listeners\":\"14401\",\"mbid\":\"fb5a9873-bdf5-40a5-a9a1-fd7188b28399\",\"url\":\"https://www.last.fm/music/Oliver+$+&+Jimi+Jules\",\"streamable\":\"0\",\"image\":[{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"small\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"medium\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"large\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"extralarge\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"mega\"}]}]},\"@attr\":{\"for\":\"oliver\"}}}";
        DataEntity entity= new Gson().fromJson(res, DataEntity.class);
        MutableLiveData<DataEntity> data = new MutableLiveData<>();
        data.setValue(entity);
        return data;
    }

    @After
    public void tearDown(){
        viewModel =null;
    }
}
