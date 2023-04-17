package com.example.playlistmaker.data

import com.example.playlistmaker.domain.api.RequestTrack
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.ActivitySearch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"

class AppleAPI : RequestTrack {

    val onResponse = {}

    private val api: SearchAPI = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create()

    override fun evaluateRequest(text: String?, callBack:ActivitySearch.CallbackResponse) {

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
                        if (trackJSON.isNotEmpty())

                        callBack.onResponse(trackJSON)

                    } else callBack.onResponse(ArrayList())
                }
            }
        })

    }
}