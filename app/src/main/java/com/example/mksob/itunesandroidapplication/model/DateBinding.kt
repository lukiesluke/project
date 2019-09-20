package com.example.mksob.itunesandroidapplication.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//@Entity(tableName = "date_table")
class DateBinding {

    @PrimaryKey(autoGenerate = true)

    private var date: String? = ""

    fun getDate(): String? {
        return date
    }

    fun setTDate(date: String?) {
        this.date = date
    }
}