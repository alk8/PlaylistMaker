package com.example.playlistmaker.presentation.di


import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.domain.usecase.MusicInteractorImpl
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.api.TracksInteracator
import com.example.playlistmaker.presentation.api.MusicInteractor
import com.example.playlistmaker.presentation.viewmodels.EmptyMediatekaFragmentModel
import com.example.playlistmaker.presentation.viewmodels.MediaViewModel
import com.example.playlistmaker.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { (runnable:Runnable) ->
        SearchViewModel(get(),get(),runnable)
    }

    viewModel { (text: String?,toast:() -> Unit) ->
        MediaViewModel(get(),get(),text,toast)
    }

    viewModel {
        EmptyMediatekaFragmentModel()
    }

    single<TracksInteracator> {
        TracksInteractorImpl(get(),get())
    }

    single<MusicInteractor> {
        MusicInteractorImpl(get())
    }

    single {
        Handler(Looper.myLooper()!!)
    }

}

