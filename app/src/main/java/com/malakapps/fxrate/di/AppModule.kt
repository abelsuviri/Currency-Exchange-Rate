package com.malakapps.fxrate.di

import com.malakapps.fxrate.navigation.AppGraphNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppGraphNavigator() = AppGraphNavigator()
}
