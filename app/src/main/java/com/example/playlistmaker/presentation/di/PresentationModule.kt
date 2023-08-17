package com.example.playlistmaker.presentation.di

import com.example.playlistmaker.domain.usecase.MusicInteractorImpl
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.api.TracksInteracator
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.viewmodels.EmptyMediatekaFragmentModel
import com.example.playlistmaker.presentation.viewmodels.FavoriteViewModel
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel { (text: String?, toast: () -> Unit) ->
        MediaViewModel(get(), text, toast)
    }

    viewModel {
        EmptyMediatekaFragmentModel()
    }

    viewModel {
        FavoriteViewModel(get())
    }

    single<TracksInteracator> {
        TracksInteractorImpl(get(), get())
    }

    single<MusicInteractor> {
        MusicInteractorImpl(get(),get(),get())
    }

}

