package com.wipro.musicworld;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.wipro.musicworld.entities.Artist;
import com.wipro.musicworld.entities.DataEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.mock.Calls;

@RunWith(AndroidJUnit4.class)
public class ArtistDataFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        activityRule.getActivity().showArtistDetailsFragment(mockResponse());
    }

    @Test
    public void checkArtistFragmentViewTest(){
        Espresso.onView(ViewMatchers.withId(R.id.listeners_key)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.listeners_name)));
    }
    @Test
    public void checkArtistDataFragmentArtistNameViewTest(){
        Espresso.onView(ViewMatchers.withId(R.id.name)).
                check(ViewAssertions.matches(ViewMatchers.withText(mockResponse().getName())));;
    }

    @Test
    public void checkArtistDataFragmentArtistListenersCountViewTest(){
        Espresso.onView(ViewMatchers.withId(R.id.listners)).
                check(ViewAssertions.matches(ViewMatchers.withText(mockResponse().getListeners())));;
    }

    public Artist mockResponse(){
        String res="{\"results\":{\"opensearch:Query\":{\"#text\":\"\",\"role\":\"request\",\"searchTerms\":\"oliver\",\"startPage\":\"2\"},\"opensearch:totalResults\":\"57198\",\"opensearch:startIndex\":\"30\",\"opensearch:itemsPerPage\":\"30\",\"artistmatches\":{\"artist\":[{\"name\":\"Oliver $ & Jimi Jules\",\"listeners\":\"14401\",\"mbid\":\"fb5a9873-bdf5-40a5-a9a1-fd7188b28399\",\"url\":\"https://www.last.fm/music/Oliver+$+&+Jimi+Jules\",\"streamable\":\"0\",\"image\":[{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"small\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"medium\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"large\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"extralarge\"},{\"#text\":\"https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png\",\"size\":\"mega\"}]}]},\"@attr\":{\"for\":\"oliver\"}}}";
        DataEntity entity= new Gson().fromJson(res, DataEntity.class);
        return entity.getResults().getArtistmatches().getArtist().get(0);
    }
}
