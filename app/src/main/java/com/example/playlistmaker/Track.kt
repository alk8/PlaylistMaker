package com.example.playlistmaker

data class Track(private val trackName: String,
            private val artistName: String,
            private val trackTime: String,
            private val artworkUrl100: String){


    fun getTrackName(): String {
        return trackName
    }

    fun getArtistName(): String {
        return artistName
    }

    fun getTrackTime(): String {
        return trackTime
    }

    fun getArtworkUrl100(): String {
        return artworkUrl100
    }

}