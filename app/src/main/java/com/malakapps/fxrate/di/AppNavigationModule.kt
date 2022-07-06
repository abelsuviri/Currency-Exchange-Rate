package com.malakapps.fxrate.di

import com.malakapps.fxrate.currency.view.exchange.IFxRateFragmentNav
import com.malakapps.fxrate.currency.view.list.ICurrencyListFragmentNav
import com.malakapps.fxrate.navigation.AppGraphNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppNavigationModule {
    @Binds
    abstract fun bindCurrencyListFragmentNav(appGraphNavigator: AppGraphNavigator): ICurrencyListFragmentNav

    @Binds
    abstract fun bindFxRateFragmentNav(appGraphNavigator: AppGraphNavigator): IFxRateFragmentNav
}
