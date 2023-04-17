package com.example.playlistmaker.presentation.api

import com.example.playlistmaker.domain.models.Track

interface TrackHistory {

    fun getHistory(): ArrayList<Track>?

    fun saveHistory(historyTrack : ArrayList<Track>)

}