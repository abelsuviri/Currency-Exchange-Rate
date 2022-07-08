package com.malakapps.fxrate.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.malakapps.fxrate.currency.data.dao.CurrencyDao
import com.malakapps.fxrate.currency.data.entity.CurrencyEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "fxrate-database"

@Database(
    entities = [CurrencyEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

abstract class FxRateDatabase : RoomDatabase() {
    class Builder(private val application: Application) {
        private val builder: RoomDatabase.Builder<FxRateDatabase>
            get() = Room.databaseBuilder(application, FxRateDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()

        fun build() = kotlin.runCatching { getInstance() }.getOrElse { getInstance(true) }

        private fun getInstance(shouldDrop: Boolean = false): FxRateDatabase {
            if (shouldDrop) {
                application.deleteDatabase(DATABASE_NAME)
            }

            return builder.build()
        }
    }

    abstract fun currencyDao(): CurrencyDao
}
