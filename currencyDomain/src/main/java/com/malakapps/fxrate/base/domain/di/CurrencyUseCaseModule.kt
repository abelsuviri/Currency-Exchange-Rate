package com.malakapps.fxrate.base.domain.di

import com.malakapps.fxrate.base.FxRepository
import com.malakapps.fxrate.base.IFxApi
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
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
}
