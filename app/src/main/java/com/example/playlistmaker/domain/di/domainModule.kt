package com.example.playlistmaker.domain.di

import com.example.playlistmaker.data.retrofit.AppleAPI
import com.example.playlistmaker.data.repository.DataBaseImpl
import com.example.playlistmaker.data.repository.MusicPlayerImpl
import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.api.PlayerMedia
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.dsl.module

val DomainModule = module {
    single<PlayerMedia> {
        MusicPlayerImpl(get(), get())
    }

    factory<Base>{
        DataBaseImpl(get(),get())
    }

    factory<GettingTracks> {
        AppleAPI(get())
    }

    single{
        MutableStateFlow(StateMusicPlayer.DEFAULT)
    }
}

