package com.example.mksob.itunesandroidapplication.db


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface DateDao {

    @Query("SELECT date_bi from DATE_TABLE")
    fun getDate(): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dateBi: DateBi)

//    fun update(dateBi: DateBi)
}