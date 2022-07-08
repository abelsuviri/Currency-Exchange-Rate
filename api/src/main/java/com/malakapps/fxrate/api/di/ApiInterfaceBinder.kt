package com.malakapps.fxrate.api.di

import com.malakapps.fxrate.api.ApiClient
import com.malakapps.fxrate.base.domain.IFxApi
import dagger.Binds
import dagger.Module

@Module
abstract class ApiInterfaceBinder {
    @Binds
    abstract fun bindFx(api: ApiClient): IFxApi
}
