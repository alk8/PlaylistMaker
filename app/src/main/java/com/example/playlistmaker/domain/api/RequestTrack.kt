package com.example.playlistmaker.domain.api
import com.example.playlistmaker.presentation.ActivitySearch

interface RequestTrack {
    fun evaluateRequest(text: String?, callBack: ActivitySearch.CallbackResponse)
}