package com.wipro.musicworld.viewmodels;

import androidx.lifecycle.ViewModelProvider;

import com.wipro.musicworld.repositories.MusicRepository;

import javax.inject.Inject;

public class ArtistListViewModelFactory implements ViewModelProvider.Factory {

    private final MusicRepository repository;

    @Inject
    public ArtistListViewModelFactory(MusicRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArtistListViewModel create(Class modelClass) {
        return new ArtistListViewModel(repository);
    }
}