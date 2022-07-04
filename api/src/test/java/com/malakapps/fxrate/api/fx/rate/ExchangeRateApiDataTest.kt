package com.malakapps.fxrate.api.fx.rate

import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ExchangeRateApiDataTest {
    @Test
    fun `when exchange rate received, create exchange rate model`() {
        // given
        val response = "{" +
            "\"query\": {" +
                "\"from\": \"GBP\"," +
                "\"to\": \"EUR\"," +
                "\"amount\": 1.5" +
            "}," +
            "\"info\": {" +
                "\"rate\": 1.160895" +
            "}," +
            "\"date\": \"2022-07-04\"," +
            "\"result\": 1.741343" +
        "}"
        val responseModel = ExchangeRateApiData(
            "2022-07-04",
            RateInfoApiData(1.160895),
            ExchangeCurrenciesApiData("GBP", "EUR"),
            1.741343
        )
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(ExchangeRateApiData::class.java)

        // when
        val apiData = adapter.fromJson(response)

        // then
        assertEquals(responseModel, apiData)
    }
}
