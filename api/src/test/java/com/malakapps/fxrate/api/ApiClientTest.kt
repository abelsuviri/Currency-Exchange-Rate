package com.malakapps.fxrate.api

import com.malakapps.fxrate.api.fx.FxService
import com.malakapps.fxrate.api.fx.currency.CurrencyApiData
import com.malakapps.fxrate.api.fx.currency.CurrencyDetailsApiData
import com.malakapps.fxrate.api.fx.rate.ExchangeCurrenciesApiData
import com.malakapps.fxrate.api.fx.rate.ExchangeRateApiData
import com.malakapps.fxrate.api.fx.rate.RateInfoApiData
import com.malakapps.fxrate.basedomain.model.Amount
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ApiClientTest {

    private val fxService = mockk<FxService>()
    private val apiClient = ApiClient(fxService)

    @Test
    fun `when currency list requested, return result`() = runBlockingTest {
        // given
        val apiData = CurrencyApiData(
            mapOf(
                Pair("EUR", CurrencyDetailsApiData("Euro", "EUR")),
                Pair("GBP", CurrencyDetailsApiData("British Pound Sterling", "GBP")),
            )
        )

        val domainModel = listOf(
            Currency("EUR", "Euro"),
            Currency("GBP", "British Pound Sterling"),
        )

        coEvery { fxService.getCurrencyList() } returns apiData

        // when
        val result = apiClient.getCurrencyList()

        // then
        assertEquals(domainModel, result)
    }

    @Test
    fun `when exchange rate requested, return result`() = runBlockingTest {
        // given
        val apiData = ExchangeRateApiData(
            "2022-07-04",
            RateInfoApiData(1.160895),
            ExchangeCurrenciesApiData("GBP", "EUR"),
            1.741343
        )

        val domainModel = ExchangeRate(
            Amount(1.741343, "EUR"),
            Pair(Amount(1.0, "GBP", 0), Amount(1.160895, "EUR", 6)),
            "2022-07-04"
        )

        coEvery { fxService.getExchangeRate("GBP", "EUR", 1.5) } returns apiData

        // when
        val result = apiClient.getExchangeRate(
            Currency("GBP", "British Pound Sterling"),
            Currency("EUR", "Euro"),
            1.5.toBigDecimal()
        )

        // then
        assertEquals(domainModel, result)
    }
}
