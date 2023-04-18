package com.example.playlistmaker.data.repository

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.api.Serializator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SerializatorTrack : Serializator {

   override fun trackToJSON(track: Track): String? {
        return Gson().toJson(track)
    }

   override fun jsonToTrack(textJSON: String?) : Track {
        return Gson().fromJson(textJSON, object : TypeToken<Track?>() {}.type)
    }
}