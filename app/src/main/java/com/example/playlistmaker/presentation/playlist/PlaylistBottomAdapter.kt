package com.example.playlistmaker.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track

class PlaylistBottomAdapter(private val albums: List<Album>, var itemClickListener: ((Int, Album) -> Unit)? = null): RecyclerView.Adapter<PlaylistBottomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistBottomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_album_line_layout,parent,false)
        return PlaylistBottomViewHolder(view)

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: PlaylistBottomViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, albums[position]) }
    }

}