package com.example.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {
    @GET("/search?entity=song")
    fun getMusic(@Query("term") text: String?): Call<TrackResponse>
}

class TrackResponse(
    val results:List<Track>
)