package com.malakapps.fxrate.currency.view.list

import com.malakapps.fxrate.base.domain.model.Currency

data class CurrencyListUiState(
    val currencyList: List<Currency>,
    val filter: String,
) {
    val filteredCurrencies = currencyList.filter { it.name.contains(filter, true) || it.code.contains(filter, true) }
}
