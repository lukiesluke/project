package com.example.mksob.itunesandroidapplication.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity(tableName = "track_info")
class Track {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "trackName")
    @SerializedName("trackName")
    private var trackName: String? = ""
    @NonNull
    @ColumnInfo(name = "primaryGenreName")
    @SerializedName("primaryGenreName")
    private var trackGenre: String? = ""
    @NonNull
    @ColumnInfo(name = "collectionPrice")
    @SerializedName("collectionPrice")
    private var collectionPrice: Double? = 0.0
    @NonNull
    @ColumnInfo(name = "artworkUrl100")
    @SerializedName("artworkUrl100")
    private var imageUrl: String? = ""
    @NonNull
    @ColumnInfo(name = "longDescription")
    @SerializedName("longDescription")
    private var trackDescription: String? = ""
    @NonNull
    @ColumnInfo(name = "releaseDate")
    @SerializedName("releaseDate")
    private var releaseDate: String? = ""


    fun getCollectionPrice(): Double? {
        return collectionPrice
    }

    fun setCollectionPrice(collectionPrice: Double?) {
        this.collectionPrice = collectionPrice
    }

    fun getTrackGenre(): String? {
        return trackGenre
    }

    fun setTrackGenre(trackGenre: String?) {
        this.trackGenre = trackGenre
    }

    fun getTrackName(): String? {
        return trackName
    }

    fun setTrackName(trackName: String?) {
        this.trackName = trackName
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }

    fun getTrackDescription(): String? {
        return trackDescription
    }

    fun setTrackDescription(trackDescription: String?) {
        this.trackDescription = trackDescription
    }

    fun setReleaseDate(releaseDate: String?) {
        this.releaseDate = releaseDate
    }

    fun getReleaseDate(): String? {
        return releaseDate
    }

    override fun toString(): String {
        return "ClassPojo [trackName = $trackName, primaryGenreName = $trackGenre, artworkUrl100 = $imageUrl, collectionPrice = " +
                "$collectionPrice, longDescription = $trackDescription, releaseDate = $releaseDate]"
    }
}