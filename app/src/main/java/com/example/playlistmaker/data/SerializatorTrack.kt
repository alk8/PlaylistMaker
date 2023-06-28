package com.example.playlistmaker.data

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Serializator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.java.KoinJavaComponent.getKoin

class SerializatorTrack(private val gson: Gson) {

    fun trackToJSON(track: Track): String? {
        return gson.toJson(track)
    }

    fun jsonToTrack(textJSON: String): Track {
        return gson.fromJson(textJSON, object : TypeToken<Track?>() {}.type)
    }

    fun tracksToJson(tracks: ArrayList<Track>?): String? {
        return gson.toJson(tracks)
    }

    fun jsonToTracks(textJSON: String): ArrayList<Track> {
        return gson.fromJson(textJSON, object : TypeToken<ArrayList<Track>?>() {}.type)
    }
}