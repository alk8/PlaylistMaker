package com.example.playlistmaker.data.retrofit

import com.example.playlistmaker.domain.api.GettingTracks
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.api.Uploader
import retrofit2.*

class AppleAPI(private val appleAPI: SearchAPI) : GettingTracks {

    override fun evaluateRequest(text: String, uploader: Uploader) {

        appleAPI.getMusic(text).enqueue(object : Callback<TrackResponse> {

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