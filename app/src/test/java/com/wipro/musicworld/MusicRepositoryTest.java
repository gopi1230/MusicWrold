package com.wipro.musicworld;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.wipro.musicworld.entities.Artist;
import com.wipro.musicworld.entities.DataEntity;
import com.wipro.musicworld.repositories.MusicRepository;
import com.wipro.musicworld.services.ArtistApi;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

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
public class MusicRepositoryTest {

    @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
  public ArtistApi artistApi;

  private MusicRepository musicRepository;

    @Before
  public void setUp(){
      MockitoAnnotations.initMocks(this);
      musicRepository = new MusicRepository(artistApi);
  }

  @Test
  public void searchArtistFailureTest(){
          String method="";
          String artist="olive";
          String apikey="cbaebad167e697788489d2b99992da60";
          String format="json";
          setupMockArtistService(mockErrorResponse());
         musicRepository.getSearchArtistResults(method,artist,apikey,format);
         Assert.assertNotNull(musicRepository.resultsMutableLiveData);
         Assert.assertNull(musicRepository.resultsMutableLiveData.getValue());
    }

    @Test
    public void searchArtistSuccessTest(){
        String method="artist.search";
        String artist="olive";
        String apikey="cbaebad167e697788489d2b99992da60";
        String format="json";
        setupMockArtistService(mockResponse());
        musicRepository.getSearchArtistResults(method,artist,apikey,format);
        Assert.assertNotNull(musicRepository.resultsMutableLiveData);
        Assert.assertEquals(1,musicRepository.resultsMutableLiveData.getValue().getResults().getArtistmatches().getArtist().size());
    }
    @Test
    public void searchArtistSuccessWithEmptyResponseTest(){
        String method="artist.search";
        String artist="";
        String apikey="cbaebad167e697788489d2b99992da60";
        String format="json";
        setupMockArtistService(mockEmptyResponse());
        musicRepository.getSearchArtistResults(method,artist,apikey,format);
        Assert.assertNotNull(musicRepository.resultsMutableLiveData);
        Assert.assertNull(musicRepository.resultsMutableLiveData.getValue().getResults());
    }
  public Call<DataEntity> mockResponse(){
        String res="{\"results\":{\"opensearch:Query\":{\"#text\":\"\",\"role\":\"request\",\"searchTerms\":\"oliver\",\"startPage\":\"2\"},\"opensearch:totalResults\":\"57198\",\"opensearch:startIndex\":\"30\",\"opensearch:itemsPerPage\":\"30\",\"artistmatches\":{\"artist\":[{\"name\":\"Oliver $ & Jimi Jules\",\"listeners\":\"14401\",\"mbid\":\"fb5a9873-bdf5-40a5-a9a1-fd7188b28399\",\"url\":\"https://www.last.fm/music/Oliver+$+&+Jimi+Jules\",\"streamable\":\"0\",\"image\":[{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"small\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"medium\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"large\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"extralarge\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"mega\"}]}]},\"@attr\":{\"for\":\"oliver\"}}}";
       DataEntity entity= new Gson().fromJson(res, DataEntity.class);
       Call<DataEntity> call = Calls.response(entity);
       return call;
    }

    public Call<DataEntity> mockEmptyResponse(){
        DataEntity entity= new DataEntity();
        Call<DataEntity> call = Calls.response(entity);
        return call;
    }

    public Call<DataEntity> mockErrorResponse(){
        final ResponseBody responseBody =
                ResponseBody.create(MediaType.parse("application/json"),
                        "{}");
        Call<DataEntity> call = Calls.response(Response.error(400, responseBody));
        return call;
    }

    private void setupMockArtistService(Call<DataEntity> call) {
        Mockito.when(artistApi.searchArtist(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),ArgumentMatchers.anyString()))
                .thenReturn(call);
    }

    @After
    public void tearDown(){
        musicRepository =null;
    }
}
