package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.models.states.StateSearch
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import com.example.playlistmaker.presentation.api.TracksInteracator
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksInteractor: TracksInteracator,
    private val runnable: Runnable
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE = 1000L
    }

    private var state = MutableLiveData<Pair<ArrayList<Track>?, StateSearch>>()

    private var trackList: ArrayList<Track>? = ArrayList()
    private var historyList: ArrayList<Track> = ArrayList()
    private lateinit var searchInternet: Job

    init {
        state.value = getDefaultState()
    }

    fun getState(): LiveData<Pair<ArrayList<Track>?, StateSearch>> = state

    fun uploadTracks(text: String) {

        if (text.isEmpty()) {
            if (state.value?.equals(StateSearch.SHOW_HISTORY) == true) {
                state.value = getDefaultState()
            }
        } else {

            // Работа с flow
            viewModelScope.launch {
                tracksInteractor
                    .uploadTracks(text)
                    .collect {
                        if (it == null) {
                            // Ошибка соединения
                            state.value = Pair(ArrayList(), StateSearch.NO_CONNECTION)
                        } else {
                            if (it.isEmpty()) {
                                // Пустой запрос
                                trackList = it
                                state.value = Pair(trackList, StateSearch.EMPTY_UPLOAD_TRACKS)
                            } else {
                                // Есть данные
                                trackList = it
                                state.value = Pair(trackList, StateSearch.SHOW_UPLOAD_TRACKS)
                            }
                        }

                    }
            }

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

    private fun getDefaultState(): Pair<ArrayList<Track>?, StateSearch> {
        return Pair(ArrayList(), StateSearch.DEFAULT)
    }

    fun trackToJSON(track: Track): String? = tracksInteractor.trackToJSON(track)

    fun searchDebounse() {
        searchInternet = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            runnable.run()
        }
    }

    fun clickDebounse(): Boolean {
        var isClick = false

        viewModelScope.launch {
            delay(CLICK_DEBOUNCE)
            isClick = true
        }
        return isClick
    }
}
