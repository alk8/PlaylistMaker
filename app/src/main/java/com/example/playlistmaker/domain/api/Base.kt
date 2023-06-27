package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface Base {
    fun getHistory(): ArrayList<Track>
    fun setHistory(trackList: ArrayList<Track>?)
}