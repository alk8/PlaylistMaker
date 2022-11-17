package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Кнопка Поиска
        findViewById<Button>(R.id.search).setOnClickListener {
            startActivity(Intent(this, activity_search::class.java))
        }
        // Кнопка Медиа
        findViewById<Button>(R.id.media).setOnClickListener {
            startActivity(Intent(this, media::class.java))
        }
        // Кнопка настройки
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, settings::class.java))
        }

    }
}