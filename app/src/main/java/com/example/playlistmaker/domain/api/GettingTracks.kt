package com.example.playlistmaker.domain.api

interface GettingTracks {
    fun evaluateRequest(text: String,uploader: Uploader)
}