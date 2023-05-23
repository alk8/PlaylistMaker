package com.example.playlistmaker.domain.usecase

import android.content.SharedPreferences
import com.example.playlistmaker.data.AppleAPI
import com.example.playlistmaker.data.repository.SerializatorTrack
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.Uploader

class TracksInteractor(private val sharedPreferences: SharedPreferences) {

    companion object {
        val serializator = SerializatorTrack()
        val api = AppleAPI()
    }

    var uploadTracks: ArrayList<Track>? = null
    fun uploadTracks(text: String) : ArrayList<Track>? {

        val uploader = object : Uploader {
            override fun getTracks(tracks: ArrayList<Track>?) {
                if (tracks == null) {
                    // Ошибка соединения
                    uploadTracks = null
                } else {
                    if (tracks.isEmpty()) {
                        // Пустой запрос
                        uploadTracks?.clear()
                    } else {
                        // Есть данные
                        uploadTracks = tracks
                    }
                }
            }
        }
        api.evaluateRequest(text, uploader)
        return uploadTracks
    }


    fun getHistory(): ArrayList<Track> {
        val stringHistory = sharedPreferences?.getString("tracksHistory", "")
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

    fun removeTrack(trackList: ArrayList<Track>?, track: Track): ArrayList<Track>? {
        if (trackList != null) {
            if (trackList.isNotEmpty()) {
                trackList.remove(track)
                trackList.add(0, track)

                if (trackList.size!! > 10) {
                    trackList.removeLast()
                }
                setHistory(trackList)
            }
        }

        return trackList
    }

}