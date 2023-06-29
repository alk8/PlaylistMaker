package com.example.playlistmaker.domain.entities

import java.text.SimpleDateFormat
import java.util.*

object FormatterTime {

    private const val TIME_DEFAULT = "00:00"
    private const val YEAR_DEFAULT = "0000"

    fun formatTime(time:String?):String{

        if (time == null) return TIME_DEFAULT

        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toLong())

    }
    fun getYear(time:String?):String{

        if (time == null) return YEAR_DEFAULT
        return time.split("-")[0]
    }
}