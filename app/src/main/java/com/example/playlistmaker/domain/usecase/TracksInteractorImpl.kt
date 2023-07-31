package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import com.example.playlistmaker.presentation.api.TracksInteracator
import kotlinx.coroutines.flow.Flow

class TracksInteractorImpl(private val dataBase : Base,val api : GettingTracks) : TracksInteracator {
    
    override suspend fun uploadTracks(text: String): Flow<ArrayList<Track>?> {
        return api.evaluateRequest(text)
    }

    override fun getHistory(): ArrayList<Track> {
       return dataBase.getHistory()
    }

    override fun setHistory(trackList: ArrayList<Track>?) {
        dataBase.setHistory(trackList)
    }

    override fun clear(): ArrayList<Track> = ArrayList()

    override fun removeTrack(trackList: ArrayList<Track>, track: Track): ArrayList<Track> {

        trackList.remove(track)
        trackList.add(0, track)

        if (trackList.size > 10) {
            trackList.removeLast()
        }
        setHistory(trackList)
        return trackList
    }

    override fun trackToJSON(track: Track): String? = dataBase.trackToJSON(track)
}