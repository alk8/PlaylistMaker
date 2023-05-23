package com.example.playlistmaker.domain.models

interface Uploader {
    fun getTracks(tracks: ArrayList<Track>?)
}