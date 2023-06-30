package com.example.playlistmaker.data.repository

import com.example.playlistmaker.domain.api.Serializator
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class SerializatorTrackImpl(private val gson: Gson) :Serializator {

    override fun trackToJSON(track: Track): String? {
        return gson.toJson(track)
    }

    override fun jsonToTrack(textJSON: String): Track {
        return try {
            gson.fromJson(textJSON, object : TypeToken<Track?>() {}.type)
        } catch (e : JsonSyntaxException){
            Track()
        }
    }

    override fun tracksToJson(tracks: ArrayList<Track>?): String? {
        return gson.toJson(tracks)
    }

    override fun jsonToTracks(textJSON: String): ArrayList<Track> {
        return try {
            return gson.fromJson(textJSON, object : TypeToken<ArrayList<Track>?>() {}.type)
        } catch (e : JsonSyntaxException){
            arrayListOf()
        }
    }
}