package com.malakapps.fxrate.base.domain

import com.malakapps.fxrate.base.domain.model.Currency

interface ICurrencyCache {
    suspend fun getCurrencyList(): List<Currency>?
    suspend fun storeCurrencies(currencyList: List<Currency>)
    suspend fun deleteAll()
}
