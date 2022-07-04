package com.malakapps.fxrate.api.fx.rate

import com.malakapps.fxrate.basedomain.model.Amount
import com.malakapps.fxrate.base.domain.model.ExchangeRate

data class ExchangeRateApiData(
    val date: String,
    val info: RateInfoApiData,
    val query: ExchangeCurrenciesApiData,
    val result: Double,
) {
    companion object {
        private const val sourceFractionDigits = 0
        private const val rateFractionDigits = 6
    }

    fun toExchangeRate() = ExchangeRate(
        Amount(result, query.to),
        Pair(Amount(1.0, query.from, sourceFractionDigits), Amount(info.rate, query.to, rateFractionDigits)),
        date,
    )
}
