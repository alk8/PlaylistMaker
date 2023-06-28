package com.example.playlistmaker.domain.di

import android.content.SharedPreferences
import com.example.playlistmaker.data.AppleAPI
import com.example.playlistmaker.data.repository.DataBase
import com.example.playlistmaker.data.repository.MusicPlayerImpl
import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.api.Player
import org.koin.dsl.module

val domainRepository = module {
    factory<Player> {
        MusicPlayerImpl(get(), get())
    }

    single<Base> { (sharedPreferences: SharedPreferences) ->
        DataBase(sharedPreferences)
    }

    single<GettingTracks> {
        AppleAPI()
    }
}

