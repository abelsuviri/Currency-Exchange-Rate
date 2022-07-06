package com.malakapps.fxrate.api.di

import com.malakapps.fxrate.api.ApiClient
import com.malakapps.fxrate.api.fx.FxService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ApiServiceModule {
    @Provides
    @Singleton
    fun apiClientBuilder() = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideApiServiceBuilder(client: OkHttpClient.Builder): Retrofit.Builder = with(client) {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(logInterceptor)

        Retrofit.Builder()
            .client(build())
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder) = ApiClient(
        builder.baseUrl("https://api.exchangerate.host").build().create(FxService::class.java)
    )
}
