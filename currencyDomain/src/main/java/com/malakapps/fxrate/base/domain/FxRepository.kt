package com.malakapps.fxrate.base.domain

import com.malakapps.fxrate.base.domain.model.Currency
import java.math.BigDecimal

class FxRepository(
    private val networkSource: IFxApi,
) {
   suspend fun getCurrencyList() = networkSource.getCurrencyList()

    suspend fun getExchangeRate(source: Currency, target: Currency, amount: BigDecimal) =
        networkSource.getExchangeRate(source, target, amount)
}
