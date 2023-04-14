package com.example.playlistmaker.data.repository

import com.example.playlistmaker.domain.models.Track

class SerializatorTrack {

    fun trackToJSON(track: Track): String? {
        return TrackRepository.gson.toJson(track)
    }

    fun jsonToTrack(textJSON: String?) : Track {
        return TrackRepository.gson.fromJson(textJSON, TrackRepository.type)
    }
}