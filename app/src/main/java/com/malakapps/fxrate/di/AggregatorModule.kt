package com.malakapps.fxrate.di

import com.malakapps.fxrate.api.di.ApiInterfaceBinder
import com.malakapps.fxrate.api.di.ApiServiceModule
import com.malakapps.fxrate.base.domain.di.CurrencyUseCaseModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [
    ApiServiceModule::class,
    ApiInterfaceBinder::class,
    CurrencyUseCaseModule::class,
])
@InstallIn(SingletonComponent::class)
object AggregatorModule
