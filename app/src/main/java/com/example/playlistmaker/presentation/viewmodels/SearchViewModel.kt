package com.example.playlistmaker.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.AppleAPI
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchViewModel(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    var trackList = MutableLiveData<ArrayList<Track>>()
    var uploadTracks = MutableLiveData<ArrayList<Track>>()

    companion object {
        val type = object : TypeToken<List<Track?>?>() {}.type
        val gson = Gson()
        val api = AppleAPI()
    }

    fun uploadTracks(text: String?) {

        val uploader = object : Uploader{
            override fun getTracks(tracks: ArrayList<Track>?) {
                if (tracks == null) {
                    // Ошибка соединения
                    uploadTracks.value = null
                } else {
                    if (tracks.isEmpty()) {
                        // Пустой запрос
                        uploadTracks.value?.clear()
                    } else {
                        // Есть данные
                        uploadTracks.value = tracks
                    }
                }
            }
        }
       api.evaluateRequest(text,uploader)
    }


    fun getHistory() {
        val stringHistory = sharedPreferences?.getString("tracksHistory", "")
        trackList.value = gson.fromJson(stringHistory, type)
    }

    fun setHistory() {
        sharedPreferences?.edit()?.putString(
            "tracksHistory", gson.toJson(
                trackList.value,
                type
            )
        )
            ?.apply()
    }

    fun clear() {
        trackList.value = ArrayList()
    }

    fun removeTrack(track: Track) {
        trackList.value?.remove(track)
        trackList.value?.add(0, track)

        if (trackList.value?.size!! > 10) {
            trackList.value?.removeLast()
        }
        setHistory()
    }
}

interface Uploader {
    fun getTracks(tracks: ArrayList<Track>?)
}