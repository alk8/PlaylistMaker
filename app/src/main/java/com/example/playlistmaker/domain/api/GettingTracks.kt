package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Uploader

interface GettingTracks {
    fun evaluateRequest(text: String,uploader: Uploader)
}