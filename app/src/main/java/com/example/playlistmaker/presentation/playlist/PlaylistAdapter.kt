package com.example.playlistmaker.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track

class PlaylistAdapter(private val albums: List<Album>, var itemClickListener: ((Int, Album) -> Unit)? = null): RecyclerView.Adapter<PlaylistViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_layout,parent,false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position,albums[position]) }
    }

}