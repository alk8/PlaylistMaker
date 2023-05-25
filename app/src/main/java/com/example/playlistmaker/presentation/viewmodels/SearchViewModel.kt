package com.example.playlistmaker.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.usecase.TracksInteractor

class SearchViewModel(
    sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val tracksInteractor: TracksInteractor = TracksInteractor(sharedPreferences)

    private var trackList = MutableLiveData<ArrayList<Track>>()
    private var uploadTracks = MutableLiveData<ArrayList<Track>>()

    fun getTrackListData(): LiveData<ArrayList<Track>> = trackList
    fun getUploadTracks(): LiveData<ArrayList<Track>> = uploadTracks

    fun uploadTracks(text: String) {
        uploadTracks.value = tracksInteractor.uploadTracks(text)
    }

    fun getHistory() {
        trackList.value = tracksInteractor.getHistory()
    }

    fun setHistory() {
        tracksInteractor.setHistory(trackList.value)
    }

    fun clear() {
        trackList.value = tracksInteractor.clear()
    }

    fun removeTrack(track: Track) {
        tracksInteractor.removeTrack(trackList.value,track)
        setHistory()
    }
}
