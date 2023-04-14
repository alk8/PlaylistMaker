package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.RequestTrack
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.ActivitySearch

class SearchTrackUseCase {
    fun execute(request: RequestTrack, query: String?,callBack: ActivitySearch.CallbackResponse) {
        return request.evaluateRequest(query,callBack)
    }

}