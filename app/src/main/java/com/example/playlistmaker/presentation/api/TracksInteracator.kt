package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import kotlinx.coroutines.flow.Flow

interface TracksInteracator {

    suspend fun uploadTracks(text: String): Flow<ArrayList<Track>?>

    fun getHistory(): ArrayList<Track>

    fun setHistory(trackList: ArrayList<Track>?)

    fun clear(): ArrayList<Track> = ArrayList()

    fun removeTrack(trackList: ArrayList<Track>, track: Track): ArrayList<Track>

    fun trackToJSON(track: Track) : String?

}