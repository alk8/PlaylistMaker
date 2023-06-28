package com.example.playlistmaker.data.di

import android.media.MediaPlayer
import com.example.playlistmaker.data.retrofit.SearchAPI
import com.example.playlistmaker.data.repository.SerializatorTrackImpl
import com.example.playlistmaker.domain.api.Serializator
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val URL = "https://itunes.apple.com/"

val dataModule = module {

    single {
        MediaPlayer()
    }
    factory {
        Gson()
    }

    single<SearchAPI> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    factory<Serializator> {
        SerializatorTrackImpl(get())
    }

}
