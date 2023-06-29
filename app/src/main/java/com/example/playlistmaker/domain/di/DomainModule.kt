package com.example.playlistmaker.domain.di

import android.content.SharedPreferences
import com.example.playlistmaker.data.retrofit.AppleAPI
import com.example.playlistmaker.data.repository.DataBaseImpl
import com.example.playlistmaker.data.repository.MusicPlayerImpl
import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.api.PlayerMedia
import org.koin.dsl.module

val DomainModule = module {
    single<PlayerMedia> {
        MusicPlayerImpl(get(), get())
    }

    factory<Base>{ (sharedPreferences: SharedPreferences) ->
        DataBaseImpl(get(),sharedPreferences)
    }

    factory<GettingTracks> {
        AppleAPI(get())
    }
}

