package com.malakapps.fxrate.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "fxrate-database"

@Database(
    entities = [],
    version = DATABASE_VERSION,
    exportSchema = false
)

abstract class FxRateDatabase : RoomDatabase() {
    class Builder(private val application: Application) {
        private val builder: RoomDatabase.Builder<FxRateDatabase>
            get() = Room.databaseBuilder(application, FxRateDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()

        fun build() = builder.build()
    }
}
