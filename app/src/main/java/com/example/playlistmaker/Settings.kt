package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ImageView>(R.id.share).setOnClickListener{

            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT,"https://practicum.yandex.ru/android-developer/")
            startActivity(intent)

        }

        findViewById<ImageView>(R.id.qa).setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.putExtra("mail","alk68@yandex.ru")
            intent.putExtra("subject","Сообщение разработчикам и разработчицам приложения Playlist Maker")
            intent.putExtra("text","Спасибо разработчикам и разработчицам за крутое приложение!")

            startActivity(Intent.createChooser(intent,"Send Email"))
        }

        findViewById<ImageView>(R.id.temp).setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://yandex.ru/legal/practicum_offer/"))
            startActivity(intent)

        }



    }
}