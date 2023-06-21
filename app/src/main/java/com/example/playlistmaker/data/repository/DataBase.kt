package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.data.SerializatorTrack
import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.Serializator
import com.example.playlistmaker.domain.models.Track

class DataBase(private val sharedPreferences: SharedPreferences) : Base {

    companion object {
        private val serializator: Serializator = SerializatorTrack()
    }

    override fun getHistory(): ArrayList<Track> {

        val stringHistory = sharedPreferences.getString("tracksHistory", "")
        if (stringHistory.isNullOrEmpty()) return ArrayList()
        return serializator.jsonToTracks(stringHistory)

    }

    override fun setHistory(trackList: ArrayList<Track>?) {
        sharedPreferences?.edit()?.putString(
            "tracksHistory", serializator.tracksToJson(
                trackList,
            )
        )
            ?.apply()
    }
}



