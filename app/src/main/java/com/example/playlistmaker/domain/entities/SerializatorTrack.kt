package com.example.playlistmaker.domain.entities

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Serializator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SerializatorTrack : Serializator {

   override fun trackToJSON(track: Track): String? {
        return Gson().toJson(track)
    }

   override fun jsonToTrack(textJSON: String) : Track {
        return Gson().fromJson(textJSON, object : TypeToken<Track?>() {}.type)
    }

    override fun tracksToJson(tracks: ArrayList<Track>?): String? {
        return Gson().toJson(tracks)
    }

    override fun jsonToTracks(textJSON: String): ArrayList<Track> {
        return Gson().fromJson(textJSON, object : TypeToken<ArrayList<Track>?>() {}.type)
    }
}