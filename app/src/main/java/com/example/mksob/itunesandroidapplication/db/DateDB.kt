package com.example.mksob.itunesandroidapplication.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class DateDB(application: Application) {
    private var dateDao: DateDao
    private var dateString: LiveData<String>

    init {
        val db = TrackRoomDatabase.getInstance(application)
        dateDao = db!!.dateDao()
        dateString = dateDao.getDate()
    }

    fun insertDate(dateBi: DateBi?) {
        InsertAsyncTask(dateDao).execute(dateBi)
    }

    fun getDate(): LiveData<String> {
        return dateString
    }

    class InsertAsyncTask internal constructor(dateDao: DateDao) : AsyncTask<DateBi, Void, Void>() {
        private var mAsyncUserDao: DateDao = dateDao

        override fun doInBackground(vararg p0: DateBi): Void? {
            mAsyncUserDao.insert(p0[0])
            return null
        }
    }
}