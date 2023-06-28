package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.Base
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import com.example.playlistmaker.presentation.api.BusinessLogic

class TracksInteractorImpl(private val dataBase : Base,val api : GettingTracks) : BusinessLogic {

    override fun uploadTracks(text: String, uploader: Uploader) {
        api.evaluateRequest(text, uploader)
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

        if (trackList.size!! > 10) {
            trackList.removeLast()
        }
        setHistory(trackList)
        return trackList
    }

    override fun trackToJSON(track: Track): String? = dataBase.trackToJSON(track)
}