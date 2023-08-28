package com.example.playlistmaker.data.db

import android.net.Uri
import com.example.playlistmaker.data.db.entity.AlbumEntity
import com.example.playlistmaker.data.db.entity.TrackEntity
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track
import java.util.Date
import java.util.UUID

class Convertor {

    fun map(track: Track): TrackEntity {

        val date = Date().time

        return TrackEntity(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = track.isFavorite,
            date = date
        )
    }

    fun map(track: TrackEntity): Track {
        return Track(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = track.isFavorite
        )
    }

    fun mapAlbum(album: Album): AlbumEntity{

        val uuid = UUID.randomUUID().toString()

        return AlbumEntity(uuid,album.nameAlbum,album.description,album.uri.toString())

    }

    fun mapAlbum(album: AlbumEntity): Album{

        return Album(album.nameAlbum,album.description, Uri.parse(album.uri),0)

    }

}