package com.example.playlistmaker.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.models.Album
import com.example.playlistmaker.domain.models.Track

class MusicAdapter : RecyclerView.Adapter<MusicViewHolder>() {
    var music: List<Track> = ArrayList()
    var itemClickListener: ((Int, Track) -> Unit)? = null
    var longClick: ((Int, Track) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_layout, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(music[position])
        holder.itemView.setOnLongClickListener{ longClick?.invoke(position, music[position])!! }
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, music[position]) }
    }

    override fun getItemCount() = music.size
}