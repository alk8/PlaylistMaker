package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface Serializator {

    fun jsonToTrack(textJSON: String?) : Track

    fun trackToJSON(track: Track): String?

    fun tracksToJson(tracks: ArrayList<Track>?) : String?

    fun jsonToTracks(textJSON: String?) : ArrayList<Track>

}