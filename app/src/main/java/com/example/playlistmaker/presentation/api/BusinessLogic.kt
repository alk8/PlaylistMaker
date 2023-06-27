package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.Uploader


interface BusinessLogic {

    fun uploadTracks(text: String, uploader: Uploader)

    fun getHistory(): ArrayList<Track>

    fun setHistory(trackList: ArrayList<Track>?)

    fun clear(): ArrayList<Track> = ArrayList()

    fun removeTrack(trackList: ArrayList<Track>, track: Track): ArrayList<Track>
}