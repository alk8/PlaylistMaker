package com.example.playlistmaker.domain.models

class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
) {

    override fun equals(other: Any?): Boolean {

        return if (other is Track){
            this.artistName == other.artistName && this.trackName == other.trackName
        }else{
            false
        }

    }

}