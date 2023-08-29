package com.example.playlistmaker.data.db.queries

data class QueryAlbumGroup(
     val nameAlbum: String,
     val description: String,
     val uri: String,
     val countTracks: String,
     val UUID: String = ""
)