package com.example.playlistmaker.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite_tracks")
data class Track(

    @PrimaryKey(autoGenerate = true)
    val id : Long,

    @ColumnInfo(name = "trackName")
    val trackName: String? = null,

    val artistName: String? = null,

    val trackTimeMillis: String? = null,

    val artworkUrl100: String = "",

    @Ignore
    val collectionName: String? = null,

    val releaseDate: String? = null,

    val primaryGenreName: String? = null,

    val country: String? = null,

    val previewUrl: String = ""
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