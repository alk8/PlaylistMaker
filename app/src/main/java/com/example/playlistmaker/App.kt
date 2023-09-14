package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.di.dataModule
import com.example.playlistmaker.domain.di.DomainModule
import com.example.playlistmaker.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

const val MEMORY = "Memory"

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = applicationContext.getSharedPreferences(MEMORY,
            MODE_PRIVATE)

        val darkThemeEnabled = sharedPreferences.getBoolean("isDarkTheme",false)

        startKoin {
            androidContext(this@App)
            // Передаём все необходимые модули
            modules(presentationModule, DomainModule, dataModule)
        }

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled){
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}