package com.example.mksob.itunesandroidapplication.model

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class ResultTrack (
    @ColumnInfo(name = "results")
    @SerializedName("results")
    val track : List<Track>)