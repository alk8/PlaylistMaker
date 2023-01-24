package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ActivitySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ImageView>(R.id.share).setOnClickListener{

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.site))
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.qa).setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("alk68@yandex.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_mail))
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.text_mail))
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.temp).setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getString(R.string.url_site))
            startActivity(intent)

        }



    }
}