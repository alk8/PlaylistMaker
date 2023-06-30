package com.example.playlistmaker.presentation.di

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.domain.usecase.MusicInteractorImpl
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.api.TracksInteracator
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val presentationModule = module {
    viewModel { (sharedPreferences: SharedPreferences,runnable:Runnable) ->
        SearchViewModel(get(parameters = { parametersOf(sharedPreferences) }),get(),runnable)
    }

    viewModel { (text: String?,toast:() -> Unit) ->
        MediaViewModel(get(),get(),text,toast)
    }

    single<TracksInteracator> { (sharedPreferences: SharedPreferences) ->
        TracksInteractorImpl(get(parameters = { parametersOf(sharedPreferences) }),get())
    }

    single<MusicInteractor> {
        MusicInteractorImpl(get())
    }

    single {
        Handler(Looper.myLooper()!!)
    }

}

