package com.example.playlistmaker.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.models.StateSearch
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import com.example.playlistmaker.presentation.api.BusinessLogic
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

class SearchViewModel(
    sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val tracksInteractor: BusinessLogic = getKoin().get(parameters = { parametersOf(sharedPreferences)})
    private var state = MutableLiveData<Pair<ArrayList<Track>?, StateSearch>>()

    private var trackList: ArrayList<Track>? = ArrayList()
    private var historyList: ArrayList<Track> = ArrayList()

    init {
        state.value = getDefaultState()
    }
    fun getState(): LiveData<Pair<ArrayList<Track>?, StateSearch>> = state

    fun uploadTracks(text: String) {

        if (text.isEmpty()){
            if (state.value?.equals(StateSearch.SHOW_HISTORY) == true) {
                state.value = getDefaultState()
            }
        }else {
            tracksInteractor.uploadTracks(text, object : Uploader {
                override fun getTracks(tracks: ArrayList<Track>?) {
                    if (tracks == null) {
                        // Ошибка соединения
                        state.value = Pair(ArrayList(), StateSearch.NO_CONNECTION)
                    } else {
                        if (tracks.isEmpty()) {
                            // Пустой запрос
                            trackList = tracks
                            state.value = Pair(trackList, StateSearch.EMPTY_UPLOAD_TRACKS)
                        } else {
                            // Есть данные
                            trackList = tracks
                            state.value = Pair(trackList, StateSearch.SHOW_UPLOAD_TRACKS)
                        }
                    }
                }
            })
        }
    }

    fun getHistory() {
        historyList = tracksInteractor.getHistory()

        state.value = if (historyList.isEmpty()) {
            Pair(historyList, StateSearch.EMPTY_HISTORY)
        } else {
            Pair(historyList, StateSearch.SHOW_HISTORY)
        }
    }

    fun setHistory() {
        tracksInteractor.setHistory(historyList)
    }

    fun clear() {
        historyList = tracksInteractor.clear()
    }

    fun removeTrack(track: Track) {
        historyList = tracksInteractor.removeTrack(historyList, track)
        setHistory()
    }

    private fun getDefaultState(): Pair<ArrayList<Track>?, StateSearch>{
        return Pair(ArrayList(),StateSearch.DEFAULT)
    }

    fun trackToJSON(track: Track) : String? = tracksInteractor.trackToJSON(track)
}
