package com.example.playlistmaker.domain.entities

import java.text.SimpleDateFormat
import java.util.*

object FormatterTime {

    fun formatTime(time:String):String{
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toLong())
    }
    fun getYear(time:String):String{
        return time.split("-")[0]
    }
}