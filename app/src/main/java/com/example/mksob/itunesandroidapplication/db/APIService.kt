package com.example.mksob.itunesandroidapplication.db

import retrofit2.Call
import retrofit2.http.GET

public interface APIService {
    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all/")
    fun makeRequest() : Call<String>
}