package com.example.playlistmaker.presentation.di

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.domain.usecase.MusicInteractorImpl
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.api.BusinessLogic
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val presentationModule = module {
    viewModel { (sharedPreferences: SharedPreferences,handler:Handler,runnable:Runnable) ->
        SearchViewModel(get(parameters = { parametersOf(sharedPreferences) }),handler,runnable)
    }

    viewModel { (text: String?) ->
        MediaViewModel(get(),get(),text)
    }

    single<BusinessLogic> { (sharedPreferences: SharedPreferences) ->
        TracksInteractorImpl(get(parameters = { parametersOf(sharedPreferences) }),get())
    }

    single<MusicInteractor> {
        MusicInteractorImpl(get())
    }

    factory {
        Handler(Looper.myLooper()!!)
    }

}

