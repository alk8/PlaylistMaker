package com.example.playlistmaker.presentation.di

import android.content.SharedPreferences
import com.example.playlistmaker.domain.usecase.MusicInteractor
import com.example.playlistmaker.domain.usecase.TracksInteractor
import com.example.playlistmaker.presentation.api.BusinessLogic
import com.example.playlistmaker.presentation.api.MusicPlayer
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationRepository = module {
    viewModel { (sharedPreferences: SharedPreferences) ->
        SearchViewModel(sharedPreferences)
    }

    viewModel { (text: String?) ->
        MediaViewModel(text)
    }

    single<BusinessLogic> { (sharedPreferences: SharedPreferences) ->
        TracksInteractor(sharedPreferences)
    }

    factory<MusicPlayer> {
        MusicInteractor()
    }
}

