package com.example.mksob.itunesandroidapplication.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.mksob.itunesandroidapplication.model.Track

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Track: Track)

    //    @Query("SELECT * from post_info_bind ORDER BY id ASC")
//    @Query("SELECT * from post_info_bind ORDER BY artistId ASC")
    @Query("SELECT * from track_info")
    fun getAllPosts(): LiveData<List<Track>>

    @Query("DELETE FROM track_info")
    fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(Track: List<Track>)
}