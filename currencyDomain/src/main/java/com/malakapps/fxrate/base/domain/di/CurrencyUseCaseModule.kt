package com.malakapps.fxrate.base.domain.di

import com.malakapps.fxrate.base.domain.FxRepository
import com.malakapps.fxrate.base.domain.IFxApi
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import dagger.Module
import dagger.Provides

@Module
class CurrencyUseCaseModule {
    @Provides
    fun provideFxRepository(
        fxApi: IFxApi,
    ) = FxRepository(fxApi)

    @Provides
    fun provideGetCurrencyListUseCase(repository: FxRepository) = GetCurrencyListUseCase(repository)

    @Provides
    fun provideGetExchangeRateUseCase(repository: FxRepository) = GetExchangeRateUseCase(repository)
}
