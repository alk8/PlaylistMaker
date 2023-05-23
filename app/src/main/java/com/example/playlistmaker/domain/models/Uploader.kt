package com.example.playlistmaker.domain.models

import com.example.playlistmaker.domain.models.Track

interface Uploader {
    fun getTracks(tracks: ArrayList<Track>?)
}