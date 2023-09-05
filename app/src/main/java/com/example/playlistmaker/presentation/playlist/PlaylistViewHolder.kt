package com.example.playlistmaker.presentation.playlist

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Album

class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = itemView.findViewById(R.id.titlePlaylist)
    private val description: TextView = itemView.findViewById(R.id.descriptionPlaylist)
    private val img: ImageView = itemView.findViewById(R.id.imgPlaylist)

    fun bind(album: Album) {
        title.text = album.nameAlbum
        description.text = album.countTracks
        if (album.uri != Uri.EMPTY) {

            Glide.with(img).load(album.uri).centerCrop()
                .placeholder(R.drawable.ic_noconnection).transform(RoundedCorners(15))
                .into(img)

        }
    }

}