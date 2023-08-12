package com.example.playlistmaker.data.db

import com.example.playlistmaker.data.db.entity.TrackEntity
import com.example.playlistmaker.domain.models.Track

class TrackConvertor {

    fun map(track:Track) : TrackEntity{
        return TrackEntity(trackName = track.trackName, artistName = track.artistName,
        trackTimeMillis = track.trackTimeMillis, artworkUrl100 = track.artworkUrl100,
        collectionName = track.collectionName, releaseDate = track.releaseDate,
        primaryGenreName = track.primaryGenreName, country = track.country,
        previewUrl = track.previewUrl, isFavorite = track.isFavorite)
    }

    fun map(track:TrackEntity) : Track{
        return Track(trackName = track.trackName, artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis, artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName, releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName, country = track.country,
            previewUrl = track.previewUrl, isFavorite = track.isFavorite)
    }

}