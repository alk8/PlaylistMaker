package com.example.playlistmaker.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.mediateka.MediatekaActivity
import com.example.playlistmaker.presentation.search.SearchActivity
import com.example.playlistmaker.presentation.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Кнопка Поиска
        findViewById<Button>(R.id.search).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        // Кнопка Медиа
        findViewById<Button>(R.id.media).setOnClickListener {
            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        // Кнопка настройки
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }
}