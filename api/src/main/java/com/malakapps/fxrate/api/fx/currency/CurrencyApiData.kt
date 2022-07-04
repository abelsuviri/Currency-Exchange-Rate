package com.malakapps.fxrate.api.fx.currency

import com.malakapps.fxrate.base.domain.model.Currency

data class CurrencyApiData(
    val symbols: Map<String, CurrencyDetailsApiData>,
) {
    fun toCurrency() = symbols.values.map { currency -> Currency(currency.code, currency.description) }
}
