package com.example.playlistmaker.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.search.ActivitySearch
import com.example.playlistmaker.presentation.settings.ActivitySettings


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Кнопка Поиска
        findViewById<Button>(R.id.search).setOnClickListener {
            startActivity(Intent(this, ActivitySearch::class.java))
        }
        // Кнопка Медиа
        findViewById<Button>(R.id.media).setOnClickListener {
        }
        // Кнопка настройки
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, ActivitySettings::class.java))
        }

    }
}