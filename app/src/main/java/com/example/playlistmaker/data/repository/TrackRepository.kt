package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.TrackHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrackRepository(private val sharedPreferences: SharedPreferences?) : TrackHistory{

    companion object {
        val type = object : TypeToken<List<Track?>?>() {}.type
        val gson = Gson()
    }

   override fun getHistory(): ArrayList<Track>? {

        return gson.fromJson(
            sharedPreferences?.getString("tracksHistory", null),
            type
        )
    }

   override fun saveHistory(historyTrack : ArrayList<Track>){
        sharedPreferences?.edit()?.putString("tracksHistory", Gson().toJson(historyTrack, type))
            ?.apply()
    }

}