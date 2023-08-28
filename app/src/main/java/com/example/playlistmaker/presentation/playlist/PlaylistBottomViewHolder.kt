package com.example.playlistmaker.presentation.playlist

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Album

class PlaylistBottomViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val nameAlbum: TextView = itemView.findViewById(R.id.bottomAlbumName)
    private val count: TextView = itemView.findViewById(R.id.BottomCountAlbum)
    private val img: ImageView = itemView.findViewById(R.id.BottomAlbumImage)

    fun bind(album: Album){
        nameAlbum.text = album.nameAlbum
        count.text = album.description
        if (album.uri != Uri.EMPTY) {
            img.setImageURI(album.uri)
        }
    }

}