package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track

interface TracksInteracator {

    suspend fun uploadTracks(text: String): ArrayList<Track>?

    fun getHistory(): ArrayList<Track>

    fun setHistory(trackList: ArrayList<Track>?)

    fun clear(): ArrayList<Track> = ArrayList()

    fun removeTrack(trackList: ArrayList<Track>, track: Track): ArrayList<Track>

    fun trackToJSON(track: Track) : String?

}