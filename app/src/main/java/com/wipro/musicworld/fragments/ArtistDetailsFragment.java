package com.wipro.musicworld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wipro.musicworld.R;
import com.wipro.musicworld.databinding.ArtistDetailsScreenBinding;
import com.wipro.musicworld.entities.Artist;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ArtistDetailsFragment extends Fragment {


    public static final String ARG_PARAM1 = "artist";

    // TODO: Rename and change types of parameters
    private Artist mSelectedArtist;
    private ArtistDetailsScreenBinding artistDetailsScreenBinding;

    @Inject
    public ArtistDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedArtist = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        artistDetailsScreenBinding = ArtistDetailsScreenBinding.inflate(inflater,container,false);
        showArtistDetails();
        return artistDetailsScreenBinding.getRoot();
    }

    private void showArtistDetails() {
        artistDetailsScreenBinding.name.setText(mSelectedArtist.getName());
        artistDetailsScreenBinding.listners.setText(mSelectedArtist.getListeners());
        artistDetailsScreenBinding.url.setText(mSelectedArtist.getUrl());
        Glide.with(getActivity()).load(mSelectedArtist.getImage().get(2).getText()).centerCrop().
                placeholder(R.mipmap.person).into(artistDetailsScreenBinding.image);
    }
}