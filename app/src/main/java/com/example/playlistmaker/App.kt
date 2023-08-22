package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.di.dataModule
import com.example.playlistmaker.domain.di.DomainModule
import com.example.playlistmaker.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            // Передаём все необходимые модули
            modules(presentationModule, DomainModule, dataModule)
        }

    }
}