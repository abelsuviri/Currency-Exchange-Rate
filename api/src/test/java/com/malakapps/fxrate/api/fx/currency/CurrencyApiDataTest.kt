package com.malakapps.fxrate.api.fx.currency

import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrencyApiDataTest {
    @Test
    fun `when currency data received, create currency model`() {
        // given
        val response = "{" +
            "\"symbols\": {" +
                "\"EUR\": {" +
                    "\"description\": \"Euro\"," +
                    "\"code\": \"EUR\"" +
                "}," +
                "\"GBP\": {" +
                    "\"description\": \"British Pound Sterling\"," +
                    "\"code\": \"GBP\"" +
                "}" +
            "}" +
        "}"
        val responseModel = CurrencyApiData(
            mapOf(
                Pair("EUR", CurrencyDetailsApiData("Euro", "EUR")),
                Pair("GBP", CurrencyDetailsApiData("British Pound Sterling", "GBP")),
            )
        )
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(CurrencyApiData::class.java)

        // when
        val apiData = adapter.fromJson(response)

        // then
        assertEquals(responseModel, apiData)
    }
}
