package com.example.mksob.itunesandroidapplication.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.os.AsyncTask
import com.example.mksob.itunesandroidapplication.model.Track

@Database(entities = arrayOf(Track::class), version = 1,exportSchema = false)
abstract  class TrackRoomDatabase: RoomDatabase() {
    abstract fun trackDao(): TrackDao


    companion object {
        private var INSTANCE: TrackRoomDatabase? = null
        val MIGRATION_1_2: Migration = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // https://developer.android.com/reference/android/arch/persistence/room/ColumnInfo
                /*

                database.execSQL("ALTER TABLE pin "
                        + " ADD COLUMN is_location_accurate INTEGER")
                 */
//            database.execSQL("ALTER TABLE pin "
//                    + " ADD COLUMN is_location_accurate INTEGER NOT NULL DEFAULT 0")
//            database.execSQL("UPDATE pin "
//                    + " SET is_location_accurate = 0 WHERE lat IS NULL")
//            database.execSQL("UPDATE pin "
//                    + " SET is_location_accurate = 1 WHERE lat IS NOT NULL")
            }
        }
        fun getInstance(context: Context): TrackRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TrackRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            TrackRoomDatabase::class.java, "post_info_database.db")
//                            .addMigrations(MIGRATION_1_2)
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