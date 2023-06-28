package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface Uploader {
    fun getTracks(tracks: ArrayList<Track>?)
}