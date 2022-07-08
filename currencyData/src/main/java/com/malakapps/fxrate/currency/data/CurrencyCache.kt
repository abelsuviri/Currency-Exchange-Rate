package com.malakapps.fxrate.currency.data

import com.malakapps.fxrate.base.data.FxRateCache
import com.malakapps.fxrate.base.domain.ICurrencyCache
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currency.data.dao.CurrencyDao
import com.malakapps.fxrate.currency.data.dao.replaceAll
import com.malakapps.fxrate.currency.data.mappings.toDomain
import com.malakapps.fxrate.currency.data.mappings.toEntity

class CurrencyCache(
    private val currencyDao: CurrencyDao,
) : FxRateCache(), ICurrencyCache {
    override suspend fun getCurrencyList() = runQuery {
        currencyDao.getAll().map { currency -> currency.toDomain() }
    }

    override suspend fun storeCurrencies(currencyList: List<Currency>) {
        runQuery { currencyDao.replaceAll(*currencyList.map { currency -> currency.toEntity() }.toTypedArray()) }
    }

    override suspend fun deleteAll() {
        runQuery { currencyDao.deleteAll() }
    }
}
