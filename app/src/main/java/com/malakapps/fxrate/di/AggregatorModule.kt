package com.malakapps.fxrate.di

import com.malakapps.fxrate.api.di.ApiInterfaceBinder
import com.malakapps.fxrate.api.di.ApiServiceModule
import com.malakapps.fxrate.base.domain.di.CurrencyUseCaseModule
import dagger.Module

@Module(includes = [
    ApiServiceModule::class,
    ApiInterfaceBinder::class,
    CurrencyUseCaseModule::class,
])
object AggregatorModule
