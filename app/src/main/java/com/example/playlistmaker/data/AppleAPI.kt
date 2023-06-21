package com.example.playlistmaker.data

import com.example.playlistmaker.data.repository.TrackResponse
import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.models.Uploader
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://itunes.apple.com/"

class AppleAPI : GettingTracks {

    private val api: SearchAPI = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create()

    override fun evaluateRequest(text: String, uploader: Uploader) {

        api.getMusic(text).enqueue(object : Callback<TrackResponse> {

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                uploader.getTracks(null)
            }

            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {

                if (response.isSuccessful) {
                    val trackJSON = response.body()?.results
                    if (trackJSON != null) {
                        uploader.getTracks(trackJSON as ArrayList<Track>?)
                    }
                }
            }
        })
    }
}
