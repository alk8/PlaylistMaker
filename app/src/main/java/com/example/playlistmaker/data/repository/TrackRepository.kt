package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class TrackRepository(private val sharedPreferences: SharedPreferences?) {
    constructor() : this(null)

    companion object {
        val type = object : TypeToken<List<Track?>?>() {}.type
        val gson = Gson()
    }

    fun getHistory(): ArrayList<Track> {

        val historyTrack : ArrayList<Track> = gson.fromJson(
            sharedPreferences?.getString("tracksHistory", null),
            type
        )

        return historyTrack
    }

    fun saveHistory(historyTrack : ArrayList<Track>){
        sharedPreferences?.edit()?.putString("tracksHistory", Gson().toJson(historyTrack, type))
            ?.apply()
    }

    fun trackToJSON(track: Track): String? {
       return gson.toJson(track)
    }

    fun jsonToTrack(textJSON: String?) : Track{
        return gson.fromJson(textJSON, type)
    }

}