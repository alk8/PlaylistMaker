package com.example.playlistmaker.presentation.di

import com.example.playlistmaker.domain.usecase.AlbumInteractorImpl
import com.example.playlistmaker.domain.usecase.MusicInteractorImpl
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.api.AlbumInteractor
import com.example.playlistmaker.presentation.api.TracksInteracator
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel { (text: String?) ->
        PlayerViewModel(get(), text,get())
    }

    viewModel {
        PlaylistsViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get(),get())
    }

    viewModel {
        NewPlaylistViewModel(get())
    }

    viewModel {
        ShowAlbumViewModel(get())
    }


    single<TracksInteracator> {
        TracksInteractorImpl(get(), get())
    }

    single<MusicInteractor> {
        MusicInteractorImpl(get(),get(),get())
    }

    single<AlbumInteractor> {
        AlbumInteractorImpl(get())
    }

}

