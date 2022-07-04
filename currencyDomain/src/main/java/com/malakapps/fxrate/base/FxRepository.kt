package com.malakapps.fxrate.base

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal

class FxRepository(
    private val networkSource: IFxApi,
) {
   suspend fun getCurrencyList() = flow<List<Currency>?> { networkSource.getCurrencyList() }

    suspend fun getExchangeRate(source: Currency, target: Currency, amount: BigDecimal) =
        flow<ExchangeRate?> { networkSource.getExchangeRate(source, target, amount) }
}
