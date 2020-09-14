package com.wipro.musicworld;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.wipro.musicworld.entities.Artist;
import com.wipro.musicworld.fragments.ArtistDetailsFragment;
import com.wipro.musicworld.fragments.ArtistListFragment;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";

    @Inject
    ArtistListFragment artistListFragment;

    @Inject
    ArtistDetailsFragment artistDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showArtistListFragment();
    }


    public void showArtistListFragment(){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.content,artistListFragment,TAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    public void showArtistDetailsFragment(Artist artist){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.hide(getSupportFragmentManager().findFragmentByTag(TAG));
        Bundle args = new Bundle();
        args.putParcelable(ArtistDetailsFragment.ARG_PARAM1, artist);
        artistDetailsFragment.setArguments(args);
        transaction.add(R.id.content, artistDetailsFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }




}