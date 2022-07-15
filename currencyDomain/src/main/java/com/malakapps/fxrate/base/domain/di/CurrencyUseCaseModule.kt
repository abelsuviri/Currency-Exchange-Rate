package com.malakapps.fxrate.base.domain.di

import com.malakapps.fxrate.base.domain.FxRepository
import com.malakapps.fxrate.base.domain.ICurrencyCache
import com.malakapps.fxrate.base.domain.IFxApi
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import com.malakapps.fxrate.basedomain.RefreshControl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CurrencyUseCaseModule {
    @Provides
    @Singleton
    fun provideFxRepository(
        fxApi: IFxApi,
        currencyCache: ICurrencyCache,
        parentControl: RefreshControl
    ) = FxRepository(fxApi, currencyCache, parentControl)

    @Provides
    fun provideGetCurrencyListUseCase(repository: FxRepository) = GetCurrencyListUseCase(repository)

    @Provides
    fun provideGetExchangeRateUseCase(repository: FxRepository) = GetExchangeRateUseCase(repository)
}
