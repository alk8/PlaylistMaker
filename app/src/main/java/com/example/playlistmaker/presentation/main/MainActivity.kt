package com.example.playlistmaker.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R
import com.example.playlistmaker.data.di.dataRepository
import com.example.playlistmaker.domain.di.domainRepository
import com.example.playlistmaker.presentation.di.presentationRepository
import com.example.playlistmaker.presentation.mediateka.ActivityMediateka
import com.example.playlistmaker.presentation.search.ActivitySearch
import com.example.playlistmaker.presentation.settings.ActivitySettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKoin {
            androidContext(this@MainActivity)
            // Передаём все необходимые модули
            modules(presentationRepository,domainRepository,dataRepository)
        }



        //Кнопка Поиска
        findViewById<Button>(R.id.search).setOnClickListener {
            startActivity(Intent(this, ActivitySearch::class.java))
        }
        // Кнопка Медиа
        findViewById<Button>(R.id.media).setOnClickListener {
            startActivity(Intent(this, ActivityMediateka::class.java))
        }
        // Кнопка настройки
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, ActivitySettings::class.java))
        }

    }
}