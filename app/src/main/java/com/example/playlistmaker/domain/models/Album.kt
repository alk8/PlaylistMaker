package com.example.playlistmaker.domain.models

import android.net.Uri

data class Album(
    val nameAlbum: String,
    val description: String,
    val uri:Uri,
    var count: String = "",
    var UUID: String = ""
)
