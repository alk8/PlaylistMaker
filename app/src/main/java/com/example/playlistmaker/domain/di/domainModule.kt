package com.example.playlistmaker.domain.di

import com.example.playlistmaker.data.retrofit.AppleAPI
import com.example.playlistmaker.data.repository.DataBaseImpl
import com.example.playlistmaker.data.repository.MusicPlayerImpl
import com.example.playlistmaker.data.repository.SettingsRepositoryImpl
import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.api.PlayerMedia
import com.example.playlistmaker.domain.api.SettingsRepository
import com.example.playlistmaker.domain.models.states.StateMusicPlayer
import com.example.playlistmaker.domain.usecase.AlbumInteractorImpl
import com.example.playlistmaker.presentation.api.AlbumInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.dsl.module
import kotlin.math.sin

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

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

}

