package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
            startActivity(Intent(this, ActivityMedia::class.java))
        }
        // Кнопка настройки
        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(Intent(this, ActivitySettings::class.java))
        }

    }
}