package com.malakapps.fxrate.currency.data.di

import com.malakapps.fxrate.base.domain.ICurrencyCache
import com.malakapps.fxrate.currency.data.CurrencyCache
import com.malakapps.fxrate.currency.data.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CurrencyDataModule {
    @Provides
    @Singleton
    fun provideCurrencyCache(currencyDao: CurrencyDao): ICurrencyCache = CurrencyCache(currencyDao)
}
