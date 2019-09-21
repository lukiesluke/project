package com.example.mksob.itunesandroidapplication.db

import com.example.mksob.itunesandroidapplication.model.ResultTrack
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all/")
    fun makeRequest(): Call<ResultTrack>
}