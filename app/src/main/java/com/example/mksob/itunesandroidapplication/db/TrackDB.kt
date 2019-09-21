package com.example.mksob.itunesandroidapplication.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.mksob.itunesandroidapplication.model.Track

class TrackDB {
    private var TrackDao: TrackDao
    private var mAllPosts: LiveData<List<Track>>
//    private  var mDetails: String

    constructor(application: Application) {
        val db = TrackRoomDatabase.getInstance(application)
        TrackDao = db!!.trackDao()
        mAllPosts = TrackDao.getAllPosts()
    }

    fun getAllPosts(): LiveData<List<Track>> {
        return mAllPosts
    }

    fun insertPost(postLists: List<Track>?) {
        InsertAsyncTask(TrackDao).execute(postLists)
    }

    class InsertAsyncTask internal constructor(trackDao: TrackDao) : AsyncTask<List<Track>, Void, Void>() {
        private var mAsyncUserDao: TrackDao = trackDao

        override fun doInBackground(vararg p0: List<Track>): Void? {
            if (p0[0] != null) {
                mAsyncUserDao.insertPosts(p0[0])
            }
            return null

        }
    }
}