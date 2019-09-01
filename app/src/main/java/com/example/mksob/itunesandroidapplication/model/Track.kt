package com.example.mksob.itunesandroidapplication.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "track_info")
class Track{

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "trackId")
    @SerializedName("trackId")
    private var id: Int? = 0
    @NonNull
    @ColumnInfo(name = "artistName")
    @SerializedName("artistName")
    private var body: String? = null
    @NonNull
    @ColumnInfo(name = "trackName")
    @SerializedName("trackName")
    private var title: String? = null
    @NonNull
    @ColumnInfo(name = "artworkUrl100")
    @SerializedName("artworkUrl100")
    private var imageUrl: String? = null


    fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String) {
        this.body = body
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }
    override fun toString(): String {
        return "ClassPojo [trackId = $id, artistName = $body, trackName = $title,artworkUrl100=$imageUrl]"
    }

}