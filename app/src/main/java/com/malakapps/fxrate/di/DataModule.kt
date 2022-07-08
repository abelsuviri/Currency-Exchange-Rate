package com.malakapps.fxrate.di

import android.app.Application
import com.malakapps.fxrate.database.FxRateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideFxRateDatabase(application: Application) = FxRateDatabase.Builder(application).build()

    @Provides
    fun provideCurrencyDao(database: FxRateDatabase) = database.currencyDao()
}
