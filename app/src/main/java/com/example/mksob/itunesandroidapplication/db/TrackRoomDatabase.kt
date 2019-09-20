package com.example.mksob.itunesandroidapplication.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import com.example.mksob.itunesandroidapplication.model.Track

@Database(entities = arrayOf(Track::class, DateBi::class), version = 10,exportSchema = false)
abstract  class TrackRoomDatabase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun dateDao(): DateDao


    companion object {
        private var INSTANCE: TrackRoomDatabase? = null
        fun getInstance(context: Context): TrackRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TrackRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            TrackRoomDatabase::class.java, "post_info_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDataBaseCallback)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        val sRoomDataBaseCallback = object:RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        };
        /* val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

               override fun onOpen(db: SupportSQLiteDatabase) {
                   super.onOpen(db)
                   InitDBAsync(INSTANCE).execute()
               }
           }*/

        class PopulateDbAsync internal constructor(db: TrackRoomDatabase) : AsyncTask<Void, Void, Void>() {

            private val mDao: TrackDao

            init {
                mDao = db.trackDao()
            }

            override fun doInBackground(vararg params: Void): Void? {
                mDao.deleteAll()
                return null
            }
        }
    }

}