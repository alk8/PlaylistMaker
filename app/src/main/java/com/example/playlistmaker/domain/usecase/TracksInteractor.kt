package com.example.playlistmaker.domain.usecase

import android.content.SharedPreferences
import com.example.playlistmaker.data.AppleAPI
import com.example.playlistmaker.domain.entities.SerializatorTrack
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.Uploader

class TracksInteractor(private val sharedPreferences: SharedPreferences) {

    companion object {
        val serializator = SerializatorTrack()
        val api = AppleAPI()
    }

    fun uploadTracks(text: String, uploader: Uploader) {
        api.evaluateRequest(text, uploader)
    }


    fun getHistory(): ArrayList<Track> {
        val stringHistory = sharedPreferences?.getString("tracksHistory", "")

        if (stringHistory?.isEmpty() == true) return ArrayList()

        return serializator.jsonToTracks(stringHistory)
    }

    fun setHistory(trackList: ArrayList<Track>?) {
        sharedPreferences?.edit()?.putString(
            "tracksHistory", serializator.tracksToJson(
                trackList,
            )
        )
            ?.apply()
    }

    fun clear(): ArrayList<Track> = ArrayList()

    fun removeTrack(trackList: ArrayList<Track>, track: Track): ArrayList<Track> {

        trackList.remove(track)
        trackList.add(0, track)

        if (trackList.size!! > 10) {
            trackList.removeLast()
        }
        setHistory(trackList)
        return trackList
    }

}