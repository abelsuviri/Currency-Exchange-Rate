package com.malakapps.fxrate.api.fx

import com.malakapps.fxrate.api.fx.currency.CurrencyApiData
import com.malakapps.fxrate.api.fx.rate.ExchangeRateApiData
import retrofit2.http.GET
import retrofit2.http.Query

interface FxService {
    @GET("/symbols")
    suspend fun getCurrencyList(): CurrencyApiData?

    @GET("/convert")
    suspend fun getExchangeRate(
        @Query("from") sourceCurrency: String,
        @Query("to") targetCurrency: String,
        @Query("amount") amount: Double,
    ): ExchangeRateApiData?
}
