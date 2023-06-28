package com.example.playlistmaker.data.di

import android.media.MediaPlayer
import com.example.playlistmaker.data.SearchAPI
import com.example.playlistmaker.data.SerializatorTrack
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val URL = "https://itunes.apple.com/"

val dataRepository = module {

    factory {
        MediaPlayer()
    }
    factory {
        Gson()
    }

    factory<SearchAPI> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    factory {
        SerializatorTrack(get())
    }
}
