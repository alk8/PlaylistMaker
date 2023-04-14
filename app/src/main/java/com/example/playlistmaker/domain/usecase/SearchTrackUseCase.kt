package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.RequestTrack
import com.example.playlistmaker.domain.models.Track

class SearchTrackUseCase {
    fun execute(request: RequestTrack, query: String?): List<Track>? {
        return request.evaluateRequest(query)
    }

}