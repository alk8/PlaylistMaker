package com.example.playlistmaker.domain.models

data class Track(

    val trackName: String = "",

    val artistName: String? = null,

    val trackTimeMillis: String? = null,

    val artworkUrl100: String = "",

    val collectionName: String? = null,

    val releaseDate: String? = null,

    val primaryGenreName: String? = null,

    val country: String? = null,

    val previewUrl: String = "",

    var isFavorite: Boolean = false

) {

    override fun equals(other: Any?): Boolean {

        return if (other is Track){
            this.artistName == other.artistName && this.trackName == other.trackName
        }else{
            false
        }

    }

    override fun hashCode(): Int {
        var result = trackName.hashCode()
        result = 31 * result + artistName.hashCode()
        return result
    }

}