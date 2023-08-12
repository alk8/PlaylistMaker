package com.example.playlistmaker.data.di

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.playlistmaker.data.db.AppDataBase
import com.example.playlistmaker.data.db.TrackConvertor
import com.example.playlistmaker.data.repository.FavoriteRepositoryImpl
import com.example.playlistmaker.data.retrofit.SearchAPI
import com.example.playlistmaker.data.repository.SerializatorTrackImpl
import com.example.playlistmaker.domain.api.FavoriteRepository
import com.example.playlistmaker.domain.api.Serializator
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val URL = "https://itunes.apple.com/"
private const val SEARCHACTIVITY = "SearchActivity"

val dataModule = module {

    factory {
        MediaPlayer()
    }
    factory {
        Gson()
    }

    factory <SearchAPI> {
        Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    factory<Serializator> {
        SerializatorTrackImpl(get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(SEARCHACTIVITY, AppCompatActivity.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java,"database.db").build()
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get(),get())
    }

    factory { TrackConvertor() }

}
