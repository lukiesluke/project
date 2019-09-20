package com.example.mksob.itunesandroidapplication.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class DateDB {
    private var dateDao: DateDao
    private var dateString: LiveData<String>

    constructor(application: Application) {
        val db = TrackRoomDatabase.getInstance(application)
        dateDao = db!!.dateDao()
        dateString = dateDao.getDate()
    }

    fun insertDate(dateBi: DateBi?) {
        DateDB.InsertAsyncTask(dateDao).execute(dateBi)
    }

    fun getDate(): LiveData<String> {
        return dateString
    }

    class InsertAsyncTask internal constructor(dateDao: DateDao) : AsyncTask<DateBi, Void, Void>() {
        private var mAsyncUserDao: DateDao = dateDao

        override fun doInBackground(vararg p0: DateBi): Void? {
            if (p0 != null) {
                mAsyncUserDao.insert(p0[0])
            }
            return null
        }
    }
}