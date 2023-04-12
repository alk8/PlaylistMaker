package com.example.playlistmaker.data

import com.example.playlistmaker.domain.api.RequestTrack
import com.example.playlistmaker.domain.models.Track
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"

class AppleAPI : RequestTrack {

    val api: SearchAPI = getAPI()

    private fun getAPI(): SearchAPI {
        val retrofit = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create()
    }

    override fun evaluateRequest(text: String?): List<Track>? {

        var listTrack: List<Track>? = null

        api.getMusic(text).enqueue(object : Callback<TrackResponse> {
            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {

                if (response.isSuccessful) {
                    val trackJSON = response.body()?.results
                    if (trackJSON != null) {
                        if (trackJSON.isNotEmpty()) listTrack = trackJSON
                    } else listTrack = ArrayList()
                }
            }
        })

        return listTrack

    }
}